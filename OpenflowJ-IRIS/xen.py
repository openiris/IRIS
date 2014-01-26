import re
import os
import copy
import glob
import string
import pprint
import types
import traceback

import spec
import openflow_converter
import openflow_types
import template

SPEC_DIR = 'specs'


'''
main entry point function of this program.
'''
try: 
  
  if os.path.exists(SPEC_DIR) and os.path.isdir(SPEC_DIR):
    
    flist = glob.glob(SPEC_DIR + '/*.txt')
    specs = []
    for file in flist: 
      sp = spec.Spec(file)
      sp.load_spec()
      specs.append( sp )
      
    # create interface files
    interface_converter = openflow_converter.Converter.create_interface_converter(specs)
    interface_converter.convert()
    
    # create types and messages files within each package directory 
    for spec in specs:
      concreate_converter = openflow_converter.Converter.create_concrete_converter(spec)
      concreate_converter.convert(interface_converter)
      
    #
    # create object factory 
    #
    
    # first, we build method map
    method_map = {}
    for x in interface_converter.interface_dic.keys():
      for spec in specs:
        stype = spec.get_type(x)
        if isinstance(stype, openflow_types.Struct):
          stripped = x[x.find('OF')+2:]
          if not method_map.get(stripped, None):
            method_map[stripped] = set()
          method_map[stripped].add( spec.get_version() )
    
    # next, we build each creation methods.
    creation_methods = []
    builder_methods = []
    version_map = {'1.0':'0x01', '1.3':'0x04'}
    tpl = template.Template.get_template('tpl/object_factory_accessor.tpl')
    tpl2 = template.Template.get_template('tpl/object_factory_builder_accessor.tpl')
    for object_name in method_map:
      versions = method_map[object_name]
      cases = []
      as_cases = []
      builder_cases = []
      for version in versions:
        if object_name == 'Match' and version == '1.0':
          cases.append('case %s: return new org.openflow.protocol.ver%s.messages.OFMatch();' % (version_map[version], version.replace('.', '_')))
          builder_cases.append('case %s: return new org.openflow.protocol.ver%s.messages.OFMatch.Builder();' % (version_map[version], version.replace('.', '_')))
        elif object_name == 'Match':
          cases.append('case %s: return new org.openflow.protocol.ver%s.messages.OFMatchOxm();' % (version_map[version], version.replace('.', '_')))
          builder_cases.append('case %s: return new org.openflow.protocol.ver%s.messages.OFMatchOxm.Builder();' % (version_map[version], version.replace('.', '_')))
        else:
          cases.append('case %s: return new org.openflow.protocol.ver%s.messages.OF%s();' % (version_map[version], version.replace('.', '_'), object_name))
          
        if object_name == 'Match' and version != '1.0':
          as_cases.append('case %s: return (OFMatchOxm) m;' % (version_map[version]) )
        else:
          as_cases.append('case %s: return (OF%s) m;' % (version_map[version], object_name) )
          
      r = tpl.safe_substitute({'cases':'\n\t\t'.join(cases),
                               'as_cases': '\n\t\t'.join(as_cases),
                               'object_name': object_name})
      creation_methods.append(r.lstrip())
      if len(builder_cases) > 0:
        r2 = tpl2.safe_substitute({'cases':'\n\t\t'.join(builder_cases),
                                   'object_name':object_name})
        creation_methods.append(r2.lstrip())
    
    # next, we load template for creating object factory.
    tpl = template.Template.get_template('tpl/object_factory.tpl')
    result = tpl.safe_substitute({'create_methods':'\n\t'.join(creation_methods)})
    
    openflow_converter.Converter.write_to_java_file('./src/org/openflow/protocol/factory/', 'OFMessageFactory', result)
      
except:
  traceback.print_exc()


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
    tpl = template.Template.get_template('tpl/object_factory_accessor.tpl')
    for object_name in method_map:
      versions = method_map[object_name]
      cases = []
      for version in versions:
        cases.append('case "%s": return new org.openflow.protocol.ver%s.messages.OF%s();' % (version, version.replace('.', '_'), object_name))
      r = tpl.safe_substitute({'cases':'\n\t\t'.join(cases),
                               'object_name': object_name})
      creation_methods.append(r.lstrip())
    
    # next, we load template for creating object factory.
    tpl = template.Template.get_template('tpl/object_factory.tpl')
    result = tpl.safe_substitute({'create_methods':'\n\t'.join(creation_methods)})
    
    openflow_converter.Converter.write_to_java_file('./src/org/openflow/protocol/factory/', 'OFMessageFactory', result)
      
except:
  traceback.print_exc()


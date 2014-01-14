import types
import re
import itertools
import pprint
import spec
import openflow_types
from template import *

def remove_prefix(str):
  n = str[str.find('_')+1:]
  if n[0].isdigit(): return 'N_' + n
  return n

class Interface:
  def __init__(self):
    ''' 
    This method currently does nothing.
    '''
    return
  

class InterfaceForEnum(Interface):
  
  def __init__(self, enum):
    '''
    Constructor.
    '''
    Interface.__init__(self)
    self.enum = enum
    self.name = enum.name
    self.constants = set()
    for i in self.enum['body']:
      n = i['name']
      n = remove_prefix(n)     # remove prefix such as OFP_ from the enum name
      if n[0].isdigit(): n = 'N_' + n
      self.constants.add( n )
    
  def __repr__(self):
    return '<InterfaceForEnum name:%s>' % (self.name)
  
  def __getitem__(self, key):
    if key == 'enum': return self.enum
    elif key == 'name': return self.name
    else:
      return None
    
  def merge(self, enum):
    for i in enum['body']:
      n = i['name']
      n = remove_prefix(n)    # remove prefix such as OFP_ from the enum name
      self.constants.add( n )  
      
  def convert(self):
    template = Template.get_template('./tpl/interface_for_types.tpl')
    result = template.safe_substitute({'interfacename':self.name, 
                                       'enumerations':',\n\t'.join(self.constants)})
    return (self.name, result)
  
class InterfaceDeclarations:
  
  def __init__(self, interface):
    '''
    constructor.
    '''
    self.interface = interface
    self.declarations = list( interface.declarations )
    
  def remove_matching_declaration(self, variable_type, variable_name):
    ''' 
    This method removes matching declarations within self.declarations.
    '''
    get_method_name = spec.Spec.convert_to_camel(variable_name, 0, 'get')+'('
    set_method_name = spec.Spec.convert_to_camel(variable_name, 0, 'set')+'('
    is_method_name = spec.Spec.convert_to_camel(variable_name, 0, 'is') + 'Supported'
    
    self.declarations = [ x for x in self.declarations if x.find(get_method_name) < 0 and x.find(set_method_name) < 0 and x.find(is_method_name) < 0]
    
  def merge(self, interface_decls):
    self.declarations = self.declarations + interface_decls.declarations
             
    
class InterfaceForStruct(Interface):
  
  def __init__(self, struct):
    '''
    Constructor.
    '''
    Interface.__init__(self)
    self.struct = struct
    self.name = struct.name
    # set of all declared names
#     self.declarations = set()
    self.declarations = []
    self.imports = set()
    self.add_declarations( self.get_declarations(struct) )
        
  def add_declarations(self, decls):
    seen = set( self.declarations )
    for i in decls:
      if i not in seen:
        self.declarations.append(i)
        seen.add(i)
          
  def get_declarations(self, struct):
    
    ret = []
    this_name = struct.name
    if this_name == 'OFMatchOxm':
      this_name = 'OFMatch'
    
    for i in struct['body']:
      if not i.get('name', None): continue
      
      if i['type'] == 'List': 
        return_type = 'List<%s>' % i['inner']
        self.imports.add('import java.util.List;')
      else:
        return_type = i['type']
        
        prefixed_types = [self.name + spec.Spec.convert_to_camel( i['name'] ),
                          spec.Spec.get_java_classname( i['name'] ),
                          i['type']]
        for prefixed_type in prefixed_types:
          t = struct.spec.get_type(prefixed_type)
          if t:
            if isinstance(t, openflow_types.Enum) and t.is_bitmask_enum():
              return_type = 'Set<%s>' % prefixed_type
              self.imports.add('import java.util.Set;')
            else:
              return_type = prefixed_type
        
        # this is for changing the return type of port number-related methods.
        if return_type in ['short', 'int']:
          if i['name'].endswith('port') or i['name'].endswith('port_number'):
            if i['name'].find('tp') < 0 and i['name'].find('transport') < 0:
              return_type = 'OFPort'
              self.imports.add('import org.openflow.util.OFPort;')
              
        # this is for changing the return time manually into OFMatch if it is OFMatchOxm
        
        if return_type == 'OFMatchOxm':
          return_type = 'OFMatch'
           
      if i.get('bitfields', None):
        bitfields = i['bitfields']
        for bitfield in bitfields:
          get_signature = 'public %s get%s();' % (return_type, spec.Spec.convert_to_camel( bitfield[0] ))
          set_signature = 'public %s set%s(%s value);' % (this_name, spec.Spec.convert_to_camel( bitfield[0] ), return_type) 
          is_signature = 'public boolean is%sSupported();' % spec.Spec.convert_to_camel( bitfield[0] )
          ret.append(get_signature)
          ret.append(set_signature)
          ret.append(is_signature)
      else:
        get_signature = 'public %s get%s();' % (return_type, spec.Spec.convert_to_camel( i['name'] ))
        set_signature = 'public %s set%s(%s value);' % (this_name, spec.Spec.convert_to_camel( i['name'] ), return_type) 
        is_signature = 'public boolean is%sSupported();' % spec.Spec.convert_to_camel( i['name'] )
        ret.append(get_signature)
        ret.append(set_signature)
        ret.append(is_signature)
    
    return ret
  
  def get_all_declarations(self):
    return InterfaceDeclarations(self)
    
  def __repr__(self):
    return '<InterfaceForStruct name:%s>' % (self.name)
  
  def __getitem__(self, key):
    if key == 'struct': return self.struct
    elif key == 'name': return self.name
    else:
      return None
    
  def merge(self, struct):
    self.add_declarations( self.get_declarations(struct) )
    
  def retrieve_set_declarations(self):
    return [x for x in self.declarations if re.search(r'\bset', x)]
  
  def retrieve_is_declarations(self):
    return [x for x in self.declarations if re.search(r'\bis\w+Supported', x)]
  
  def convert(self):
    is_ofmatch = False
    template = Template.get_template('./tpl/interface_for_structs.tpl')
    if (self.name == 'OFMatch' and self.struct.spec.get_version() == '1.0') or self.name == 'OFMatchOxm':
      template = Template.get_template('tpl/interface_for_structs_ofmatch.tpl')
      set_accessors = self.retrieve_set_declarations()
      is_accessors = self.retrieve_is_declarations()
      for name in [ 'OFMatch', 'OFMatchOxm' ]:
        set_accessors = [re.sub(r'\b%s\b' % name, 'Builder', x) for x in set_accessors]
      is_ofmatch = True
    
    if self.struct.supertype:
      inherit = 'extends %s' % self.struct.supertype
    else:
      inherit = ''
      if self.name == 'OFMessage': inherit = 'extends org.openflow.protocol.OFMessage'
      
    if is_ofmatch:
      result = template.safe_substitute({'typename':self.name, 
                                         'imports':'\n'.join(self.imports),
                                         'inherit':inherit,
                                         'accessors':'\n\n\t'.join(self.declarations),
                                         'builder_accessors':'\n\t\t'.join(set_accessors + is_accessors)})
    else:
      result = template.safe_substitute({'typename':self.name, 
                                         'imports':'\n'.join(self.imports),
                                         'inherit':inherit,
                                         'accessors':'\n\t'.join(self.declarations)})
    return (self.name, result)
  
  
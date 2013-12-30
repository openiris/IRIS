import types
import re
import itertools
import pprint
import spec
from template import *

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
      self.constants.add( i['name'] )
    
  def __repr__(self):
    return '<InterfaceForEnum name:%s>' % (self.name)
  
  def __getitem__(self, key):
    if key == 'enum': return self.enum
    elif key == 'name': return self.name
    else:
      return None
    
  def merge(self, enum):
    for i in enum['body']:
      self.constants.add( i['name'] )  
      
  def convert(self):
    template = Template.get_template('./tpl/interface_for_types.tpl')
    result = template.safe_substitute({'interfacename':self.name, 
                                       'enumerations':',\n\t'.join(self.constants)})
    return (self.name, result)
    
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
    
    for i in struct['body']:
      if not i.get('name', None): continue
      
      if i['type'] == 'List': 
        return_type = 'List<%s>' % i['inner']
        self.imports.add('import java.util.List;')
      else:
        return_type = i['type']
        prefixed_type = self.name + spec.Spec.convert_to_camel( i['name'] )
        if struct.spec.get_type(prefixed_type):
          return_type = prefixed_type
         
      get_signature = 'public %s get%s();' % (return_type, spec.Spec.convert_to_camel( i['name'] ))
      set_signature = 'public %s set%s(%s value);' % (struct.name, spec.Spec.convert_to_camel( i['name'] ), return_type) 
        
      ret.append(get_signature)
      ret.append(set_signature)
    
    return ret
      
  def __repr__(self):
    return '<InterfaceForStruct name:%s>' % (self.name)
  
  def __getitem__(self, key):
    if key == 'struct': return self.struct
    elif key == 'name': return self.name
    else:
      return None
    
  def merge(self, struct):
    self.add_declarations( self.get_declarations(struct) )
  
  def convert(self):
    template = Template.get_template('./tpl/interface_for_structs.tpl')
    if self.struct.supertype:
      inherit = 'extends %s' % self.struct.supertype
    else:
      inherit = ''
    result = template.safe_substitute({'typename':self.name, 
                                       'imports':'\n'.join(self.imports),
                                       'inherit':inherit,
                                       'accessors':'\n\n\t'.join(self.declarations)})
    return (self.name, result)
  
  
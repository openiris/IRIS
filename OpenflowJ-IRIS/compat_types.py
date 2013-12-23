import types
import re
import itertools
import pprint
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
    self.decl_names = set()
    self.decl_refs = {}

    for i in self.struct['body']:
      if not i.get('name', None): 
        continue                        # just skip padding declarations
      self.decl_names.add(i['name'])
      list = self.decl_refs.get(i['name'], None)
      if not list:
        list = []
        self.decl_refs[ i['name'] ] = list
      list.append( i )
    
  def __repr__(self):
    return '<InterfaceForStruct name:%s>' % (self.name)
  
  def __getitem__(self, key):
    if key == 'struct': return self.struct
    elif key == 'name': return self.name
    else:
      return None
    
  def merge(self, struct):
    '''
    TBD
    '''
    return
  
  def convert(self):
    return (None, None)
  
  
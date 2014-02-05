
from spec import *
from template import *
from openflow_types import *
from compat_types import *

class Converter:
  
  @staticmethod
  def write_to_java_file(path, filename, content):
    # temporary fix to compare with original files!
    write_to = path + '/' + filename + '.java'
    file = open(write_to, 'w')
    file.write(content)
    file.close()
  
  @staticmethod
  def create_concrete_converter(spec):
    return ConcreteConverter(spec)
  
  @staticmethod
  def create_interface_converter(specs):
    return InterfaceConverter(specs)
  
class InterfaceConverter(Converter):
  '''
  This converter generates interface definitions
  '''
  
  def __init__(self, specs):
    '''
    Constructor.
    '''
    self.specs = specs
    self.interface_dic = {}
    
  def convert(self):
    '''
    This method generates interface class files
    and returns itself for later use by ConcreteConverter. 
    '''    
    for spec in self.specs:
      # in this loop, we merge definitions step-by-step.
      all_items = spec.get_all_items();

      for item in all_items:
        if isinstance(item, Enum):
          if not self.interface_dic.get(item.name, None):
            intf = InterfaceForEnum(item)
            self.interface_dic[ item.name ] = intf
          else:
            intf = self.interface_dic[ item.name ]
            intf.merge( item )
        else:
          if not self.interface_dic.get(item.name, None):
            if item.name == 'OFMatch' and spec.get_version() != '1.0':
              continue
            intf = InterfaceForStruct(item)
            self.interface_dic[ item.name ] = intf
          else:
            if item.name == 'OFMatch' and spec.get_version() != '1.0':
              continue
            intf = self.interface_dic[ item.name ]
            intf.merge( item )
              
          if item.name == 'OFMatchOxm':
            intf = self.interface_dic[ 'OFMatch' ]
            intf.merge( item )
            self.interface_dic[ item.name ].merge( intf.struct )
            
    # for every interface within the dictionary,
    # we create an interface file and save it into org.openflow.protocol.interfaces.
    path = './src/org/openflow/protocol/interfaces/'
    Spec.create_directory(path) # weird (because it's Spec api -_-;)
    
    for key in self.interface_dic.keys():
      interface = self.interface_dic[ key ]
      (typename, result) = interface.convert();
      if typename == None or result == None: continue;
      Converter.write_to_java_file(path, typename, result)
      
  def get_interface(self, name):
    '''
    This method lookup an interface definition by its name.
    '''
    return self.interface_dic.get(name, None)


class ConcreteConverter(Converter):
  '''
  This converter is for concrete classes such as Enum and Struct
  '''
  
  def __init__(self, spec):
    '''
    constructor
    '''
    self.spec = spec
  
  
  def convert(self, interface_converter):
    '''
    This method generates enum and struct files.
    '''
    all_items = self.spec.get_all_items()
    for item in all_items:
      if isinstance(item, Enum):
        path = Spec.create_directory(self.spec.get_root_path() + 'types')
        (typename, result) = item.convert(path)
      else:
        path = Spec.create_directory(self.spec.get_root_path() + 'messages')
        (typename, result) = item.convert(path, interface_converter)

      if typename == None or result == None: continue
      Converter.write_to_java_file(path, typename, result)
        
        
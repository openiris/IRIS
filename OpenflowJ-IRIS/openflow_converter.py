
from spec import *
from template import *
from openflow_types import *

class Converter:
  
  @staticmethod
  def create_converter(spec):
#     if spec.get_version() == '1.0':
#       return Converter_1_0(spec)
#     else:
#       return None
    return Converter_1_0(spec)
  
#
# openflow universal converter (version 1_0)
# 

class Converter_1_0(Converter):
  
  def __init__(self, spec):
    '''
    constructor
    '''
    self.spec = spec
  
  @staticmethod
  def write_to_java_file(path, filename, content):
  	# temporary fix to compare with original files!
    write_to = path + '/' + filename + '.java'
    file = open(write_to, 'w')
    file.write(content)
    file.close()
      
  def convert(self):
    all_items = self.spec.get_all_items()
    for item in all_items:
      paths = []
      if isinstance(item, Enum):
        paths.append( Spec.create_directory(self.spec.get_root_path() + 'types') )
      else:
        paths.append( Spec.create_directory(self.spec.get_root_path() + 'messages') )
        paths.append( Spec.create_directory(self.spec.get_root_path() + 'interfaces') )
      for path in paths:
        (typename, result) = item.convert(path)
        if typename == None or result == None: continue
        Converter_1_0.write_to_java_file(path, typename, result)
        
        
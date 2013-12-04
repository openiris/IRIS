
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
      
  def process_struct(self, path, definition):
    '''
    This function processes struct and creates Java code for each.
    '''
#     print definition['name']
    if not Struct.is_topmost_struct(definition):              # there's no supertype. 
      superdef = Struct.get_supertype(self.spec, definition)  # superclass definition
      supername = Struct.get_java_supertype(definition)       # superclass name
      inherit_method = 'extends'
    else:
      superdef = None
      supername = ''
      inherit_method = ''
      
#     superreadfrom = ''
    superwriteto = ''
    
    # if supertype is found, reduce the subtype implementation
    if superdef:
      Struct.reduce(self.spec, superdef, definition)
#       superreadfrom = 'super.readFrom(data);'
      superwriteto = 'super.writeTo(data);'
      
    template = Template.get_template('tpl/struct.tpl')
     
    name = definition['name']
 
    packagename = Type.get_java_packagename(path)
    typename = Type.get_java_classname(name)
    importname = 'import ' + packagename[:packagename.rfind('.')] + '.types.*;'
    
    minimumlength = Struct.get_minimum_length(self.spec, definition)
    component_map = Struct.get_struct_components(self.spec, definition)
    imports = component_map['imports']
    imports.append(importname)
    imports = '\n'.join(imports)
    
    result = template.safe_substitute({
      'typename':typename, 'packagename':packagename, 'imports':imports,
      'minimumlength':minimumlength, 'declarations':component_map['declarations'],
      'constructor':component_map['constructor'],
      'copyconstructor':component_map['copyconstructor'],
      'accessors':component_map['accessors'],
      'hashcode':component_map['hashcode'], 'equals':component_map['equals'],
      'tostring':component_map['tostring'], 'readfrom':component_map['readfrom'],
      'writeto':component_map['writeto'],
      'inherit_method':inherit_method, 'supertype':supername, 
      'superwriteto':superwriteto,
      'implements':component_map['implements'],
      'computelength':component_map['computelength']
    })
    return (typename, result)
    

  def convert(self):
    all_items = self.spec.get_all_items()
    for item in all_items:
      if isinstance(item, Enum):
        path = Spec.create_directory(self.spec.get_root_path() + 'types')
      else:
        path = Spec.create_directory(self.spec.get_root_path() + 'messages')
      (typename, result) = item.convert(path)
      if typename == None: continue
      Converter_1_0.write_to_java_file(path, typename, result)
        
        
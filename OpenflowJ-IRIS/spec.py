
import re
import os
import types
import pprint
import openflow_types
import traceback

class Spec:
  ''' 
  This class handles specification file related chores
  '''
  
  BLOCK_PATTERN = re.compile(
    r'(?P<type>struct|enum)\s+(?P<name>\w+)(?P<enum_arg>\([\w\,\=\.\s]+\)){0,1}\s*(:\s*(?P<supertype>\w+)){0,1}\s*\{(?P<body>[\w\s\=\?\,:;\(\)]+)\};'
  )
  
  @staticmethod
  def get_java_packagename(path):
    return path.replace('./src/', '').replace('/', '.').strip('.')
  
  @staticmethod
  def get_java_classname(definition):
    '''
    definition is a map item inside Spec.all_items
    '''
    if type(definition) == types.DictionaryType:
      return Spec.convert_to_camel(definition['name'], 0, 'OF')
    elif type(definition) == types.StringType:
      return Spec.convert_to_camel(definition, 0, 'OF')
    
  @staticmethod
  def get_object_type(name):
    list = ['byte', 'short', 'int', 'long']
    util = ['Byte', 'Short', 'Integer', 'Long']
    return util[ list.index(name) ]
    
  @staticmethod
  def get_innertype(str):
    r = re.search(r'\(of_(?P<inner>\w+)_t\)', str)
    if r != None:
      return Spec.get_java_classname(r.group('inner'))
    else:
      r = re.search(r'\((?P<inner>\w+)\)', str)
      if r != None:
        return Spec.get_object_type(Spec.convert_to_java_type(r.group('inner')))
    return None
  
  @staticmethod
  def convert_to_java_type(value):
    '''
    This method coverts the given name into java type name
    this method is not used to convert the name of the enum or struct.
    '''
    if type(value) == types.StringType:
      if value == 'uint8_t': return Spec.convert_to_java_type(1)
      elif value == 'uint16_t': return Spec.convert_to_java_type(2)
      elif value == 'uint32_t': return Spec.convert_to_java_type(4)
      elif value == 'uint64_t': return Spec.convert_to_java_type(8)
      elif value == 'of_ipv4_t': return Spec.convert_to_java_type(4)
      elif value == 'of_desc_str_t': return 'String'
      elif value == 'of_serial_num_t': return 'String'
      elif value == 'of_mac_addr_t': return 'byte[]'
      elif value == 'of_octets_t': return 'byte[]'
      elif value.endswith('_name_t'): return 'byte[]'
      elif value.startswith('list('): return 'List'
      elif value.startswith('of_'):
        real = re.search(r'of_(?P<inner>\w+)_t', value).group('inner')
        return Spec.convert_to_camel(real, 0, 'OF')
      else:
        # print 't == ', value
        return 'x'
    elif type(value) == types.IntType:
      if value == 1: return 'byte'
      elif value == 2: return 'short'
      elif value == 4: return 'int'
      elif value == 8: return 'long'
      else:
        return 'x'
  
  @staticmethod
  def convert_to_camel(name, start=0, prefix=None):
    '''
    This method converts a c_style name into Java style name (camel case)
    '''
    n = name.lower()
    ret = ''
    splitted = n.split('_')[start:]
    for i in splitted:
      ret += i.capitalize()
    if prefix:
      return prefix + ret
    else:
      return ret
  
  def __init__(self, file_path):
    # type dictionary
    self.type_dic = {}
    self.spec_file_path = file_path
    filename = os.path.basename(self.spec_file_path)
    self.version = filename.split('-')[1].split('.txt')[0]
    self.all_items = []
    self.root_path = './src/org/openflow/protocol/' + self.get_version_str(self.version) + '/'
    self.type_size_cache = {}
    
  def get_all_items(self):
    return self.all_items
  
  def get_type_dic(self):
    return self.type_dic
  
  def get_root_path(self):
    return self.root_path
  
  def get_version(self):
    return self.version
  
  def get_definition(self, str):
    ''' 
    This method search through the OFDefinitions to lookup the value assigned to a given name.
    '''
    if self.type_dic.get('OFDefinitions', None):
      for i in self.type_dic['OFDefinitions'].body:
        if i['name'] == str:
          return int(i['value'])
    return None
  
  def get_type(self, name):
    '''
    This method returns a type associated with the name. 
    If there's no such type for the name, None is returned.
    '''
    return self.type_dic.get(name, None)
  
  def is_enum(self, name):
    if self.type_dic.get(name, None):
      if self.type_dic[name]['type'] == 'enum':
        return True
    return False
  
  def get_type_size(self, str):
    '''
    returns the size of the given type name.
    '''
    if self.type_size_cache.get(str, None):
      return self.type_size_cache[str]
    
    ret = None
    if str == 'uint8_t': ret = 1
    elif str =='byte' or str == 'Byte': ret = 1
    elif str == 'uint16_t': ret = 2
    elif str == 'short' or str == 'Short': ret = 2
    elif str == 'uint32_t': ret = 4
    elif str == 'int' or str == 'Integer': ret = 4
    elif str == 'uint64_t': ret = 8
    elif str == 'long' or str == 'Long': ret = 8
    else:
      if self.type_size_cache.get(str, None):
        ret = self.type_size_cache[str]
      elif self.type_dic.get(str, None):
        entry = self.type_dic[str]
        if isinstance(entry, openflow_types.Enum) and entry.enum_args:
          ret = self.get_type_size(entry.enum_args['wire_type'])
      elif str == 'of_mac_addr_t':
        ret = self.get_definition('OFP_ETH_ALEN')
      elif str == 'of_port_name_t':
        ret = self.get_definition('OFP_MAX_PORT_NAME_LEN')
      elif str == 'of_table_name_t':
        ret = self.get_definition('OFP_MAX_TABLE_NAME_LEN')
      elif str == 'of_desc_str_t':
        ret = self.get_definition('DESCRIPTION_STRING_LEN')
      elif str == 'of_serial_num_t':
        ret = self.get_definition('SERIAL_NUMBER_LEN')
      elif str == 'of_ipv4_t':
        ret = 4
        
    if ret > 0 : self.type_size_cache[str] = ret
    return ret
  
#   def get_supertypes(self, definition):
#     r = []
#     while definition.get('supertype', None):
#       s = self.type_dic[definition['supertype']]
#       r.append( s )
#       definition = s
#     r.reverse()
#     return r
  
  def get_minimum_length(self, definition):
    '''
    This method calculates the minimum length of the struct definition.
    '''
    ret = 0;
    tl = self.get_supertypes(definition);
    tl.append(definition)
    ret = 0
    for i in tl:
      for j in i['body']:
        if j['type'] != 'pad':
    #       print '(%s == %d)' % (i['type'], get_type_size(i['type']))
          ret += self.get_type_size(j['type'])
        else:
          ret += int(j['value'])
    
    if ret > 0 : self.type_size_cache[definition['name']] = ret
    return ret
    
  @staticmethod
  def get_version_str(version):
    '''
    This function coverts a string like '1.0' into 'ver1_0'.
    '''
    return "ver" + version.replace('.', '_')

  @staticmethod
  def create_directory(path):
    if not os.path.exists(path):
      os.makedirs(path, 0777)
    return path
  
  @staticmethod
  def parse_enum_args(arg):
    '''
    this function parse enumeration argument information from given string.
    '''
    unpacked = {}
    for x in re.split(",\s*", arg.strip('()')):
      splitted = re.split("=\s*", x.strip())
      n = splitted[0].strip()
      v = splitted[1].strip()
      if n == 'wire_type': 
        v = Spec.convert_to_java_type(v)
        unpacked [n] = v
      elif n == 'request' or n == 'reply': 
        t = v.split('.')
        t0 = t[0].strip()
        t0 = t0[t0.find('_')+1:]
        t1 = t[1].strip()
        t1 = t1[t1.find('_')+1:]
        unpacked[n+'_type'] = Spec.get_java_classname(t0)
        unpacked[n+'_value'] = t1
      else:
        unpacked[ n ] = v
      
    return unpacked

  def parse_body(self, t, arg):
    '''
    this function parse body of the enum/struct definition
    '''
    if t == 'enum':
      r = []
      for x in arg.split(","):
        x = x.strip();
        if len(x) == 0: 
          continue
        splitted = re.split("[\(\)=]", x);
        if len(splitted) > 2:
          tokens = []
          for y in splitted:
            stripped = y.strip();
            if len(stripped) == 0:
              continue
            else:
              tokens.append( stripped )
          r.append( {'name': tokens[0].strip(), 'value': tokens[3].strip(), tokens[1].strip(): tokens[2].strip()} )
        else:
          r.append( {'name': splitted[0].strip(), 'value': splitted[1].strip()} )
      return r
    elif t == 'struct':
      r = []
      for x in arg.split(';'):
        seg = x.strip();
        if len(seg) == 0 : continue
        
        splitted = seg.split('==')
        to_append = {}
        if len(splitted) == 2:
          tn = re.split('\s+', splitted[0].strip())
          typename = tn[0].strip()
          to_append['type'] = Spec.convert_to_java_type( typename )
          if to_append['type'] == 'List':
            to_append['inner'] = Spec.get_innertype( typename )
            to_append['length'] = None
          else:
            to_append['length'] = self.get_type_size( typename )
            if to_append['length'] == None: 
              to_append['length'] = self.get_type_size( to_append['type'] )
          to_append['name'] = tn[1].strip()
          to_append['value'] = splitted[1].strip()
          
        else:
          tn = re.split('\s+', splitted[0].strip())
          if len(tn) == 1:
            to_append['type'] = 'pad'
            to_append['length'] = re.search('\((?P<len>[0-9]+)\)', tn[0]).group('len')
          elif len(tn) == 2:
            typename = tn[0].strip()
            to_append['type'] = Spec.convert_to_java_type( typename )
            if to_append['type'] == 'List':
              to_append['inner'] = Spec.get_innertype( typename )
              to_append['length'] = None
            else:
              to_append['length'] = self.get_type_size( typename )
              if to_append['length'] == None:
                to_append['length'] = self.get_type_size( to_append['type'] )
            to_append['name'] = tn[1].strip()
           
          else:
            # this probably be bitfield declarations.
            # tn[0] represents the typename that holds every value,
            # end the others are the parts of the bitfield.
            typename = tn[0].strip()
            to_append['type'] = Spec.convert_to_java_type( typename )
            var_name = ''
            bitfields = []
            for p in range(1, len(tn)):
              bfield = tn[p].strip().split(':')
              bfieldn = bfield[0].strip()
              bfieldv = bfield[1].strip()
              bitfields.append( (bfieldn, bfieldv) )  # tuple of bitfield name, and bitfield size
              var_name += bfieldn + '_'
            to_append['name'] = var_name.strip('_')
            to_append['length'] = self.get_type_size( typename )
            if to_append['length'] == None:
              # I think this procedure is unnecessary, but do it anyway...
              to_append['length'] = self.get_type_size( to_append['type'] )
            to_append['bitfields'] = bitfields
            
        # now, append
        r.append( to_append )
        
      return r
  
  def process_spec(self, spec_file, version, path):
    '''
    process a specification file according to its version.
    '''
    f = open(spec_file, 'r')
    whole_file = f.read()
    iter = re.finditer(Spec.BLOCK_PATTERN, whole_file)
    
    while True:
      try:
        item = next(iter)
        t = item.group('type')
        n = item.group('name')
        n = n[n.find('_')+1:]         # strip all prefix such as of_ from their names
        if n == 'header': n = 'message'
        n = Spec.get_java_classname(n)
        e = item.group('enum_arg')
        s = item.group('supertype')
        if s: s = s[s.find('_')+1:]
        if s == 'header': s = 'message'
        s = Spec.get_java_classname(s)
        b = item.group('body')
  
        if e:
          e = Spec.parse_enum_args(e)
          
        if b:
          b = self.parse_body(t, b)
          
        new_item = {
          'type': t, 
          'name': n,
          'enum_args': e,
          'supertype': s,
          'body': b
        }
        
#         print new_item

        if new_item['type'] == 'enum':
          en = openflow_types.Enum(self, new_item)
          self.all_items.append( en )
          self.type_dic[n] = en
        else:
          st = openflow_types.Struct(self, new_item)
          self.all_items.append( st )
          self.type_dic[n] = st
                  
      except Exception, e:
        # when there's no more item, an exception is thrown
        if isinstance(e, StopIteration): break
        print e
        traceback.print_exc()
        exit()
        break
  
#     pprint.pprint(self.type_dic)
    return self.all_items
  
  def load_spec(self):
    ''' 
    load specification from the self.spec_file_path
    '''    
    self.create_directory(self.get_root_path())
    return self.process_spec(self.spec_file_path, self.version, self.get_root_path())
    

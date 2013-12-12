import types
import re
import itertools
import pprint
from template import *

class Type:
  def __init__(self):
    ''' 
    This method currently does nothing.
    '''
    return
  
  @staticmethod
  def is_primitive_java_type(name):
    if name in ['byte', 'short', 'int', 'long', 'byte[]']:
      return True
    return False
  
  @staticmethod
  def is_value_java_type(name):
    if name in ['byte', 'short', 'int', 'long']:
      return True
    return False
  
  @staticmethod
  def has_longer_java_type(name):
    if name in ['byte', 'short', 'int']:
      return True
    
  @staticmethod
  def get_longer_java_type(name):
    list = ['byte', 'short', 'int', 'long']
    return list[ list.index(name) + 1 ]
  
  @staticmethod 
  def get_java_util_type(name):
    list = ['byte', 'short', 'int', 'long']
    util = ['U8', 'U16', 'U32', 'U64']
    return util[ list.index(name) ]
  
  @staticmethod
  def get_object_type(name):
    list = ['byte', 'short', 'int', 'long']
    util = ['Byte', 'Short', 'Integer', 'Long']
    return util[ list.index(name) ]
  
# end of Type definition
  
class Enum(Type):
  def __init__(self, spec, definition):
    '''
    Constructor.
    '''
    Type.__init__(self)
    self.spec = spec
    self.name = definition['name']
    self.body = definition['body']
    self.enum_args = definition['enum_args']
    
  def __repr__(self):
    return '<Enum name:%s, enum_args:%s, body:%s>' % (self.name,self.enum_args,self.body)
  
  def __getitem__(self, key):
    if key == 'spec': return self.spec
    elif key == 'name': return self.name
    elif key == 'body': return self.body
    elif key == 'enum_args': return self.enum_args
    else:
      return None
    
  def is_macro_enum(self):
    return self.enum_args == None
  
  def is_bitmask_enum(self):
    return self.enum_args and self.enum_args.get('bitmask', None)

  def is_plain_enum(self):
    return not self.name.endswith('Type')  

  def is_generative_enum(self):
    return self.name.endswith('Type')
  
  def is_generative_enum_rnr(self):
    return self.enum_args.get('request_type', None) != None
  
  def get_length(self):
    return len(self.body)
  
  def get_java_lines(self, forced_type = None):
    '''
    This method converts body lines into assignments or normal enum declarations.
    '''
    ret = ''
    if self.is_macro_enum():
      forced_type = 'int'
      
    if forced_type:
      for i in self.body:
        ret += '%s\t%s\t=\t%s;\n\t' % (forced_type, i['name'], i['value'])
      return ret
    else:
      for i in self.body:
        ret += '%s\t(%s),\n\t' % (i['name'], i['value'])
      return ret.strip(',\n\t')
    
  def get_java_representation(self):
    '''
    get underlying java representation of this enum
    '''
    return self.enum_args.get('wire_type', None)

  def get_java_supertype(self):
    if self.name.endswith('Type'):
      return self.name.split('Type')[0]
    else:
      return None
    
  def get_java_generative_lines(self):
    '''
    This method generates enum lines for generative enums
    '''
    one = Template.get_template('tpl/gen_enum_line.tpl')
    supertype = self.get_java_supertype()
    ret = ''
    for i in self.body:
      n = i['name']
      n = n[n.find('_')+1:]
      v = i['value']
      typename = ''
      if supertype != 'OFMessage':
        typename = supertype
      else:
        typename = 'OF'
      typename = self.spec.convert_to_camel(n, 0, typename)
      ret += one.substitute({'n':n, 'v':v, 'typename':typename, 'supertype':supertype}).rstrip() + '\n'
    
    return ret.strip(', \n\t')
      
  def get_java_rnr_diff_type(self):
    return self.enum_args.get('request_type', None)
  
  def get_java_rnr_diff_request_value(self):
    return self.enum_args.get('request_value', None)

  def get_java_rnr_diff_reply_value(self):
    return self.enum_args.get('reply_value', None)

  def get_java_generative_rnr_lines(self):
    '''
    This method generates enum lines for generative enums
    '''
    two = Template.get_template('tpl/gen_enum_line_rnr.tpl')
    supertype = self.get_java_supertype()
    
    ret = ''
    for i in self.body:
      v = i['value']            # enum value
      n = i['name']             # enum name
      n = n[n.find('_')+1:]     # remove prefix such as OFP_ from the enum name
      typename = ''
      if supertype != 'OFMessage':
        typename = supertype
      else:
        typename = 'OF'
      
      typename = self.spec.convert_to_camel(n, 0, typename) 
      ret += two.substitute({'n':n, 'v':v, 'typename':typename, 'supertype':supertype}).rstrip() + '\n'
      
    return ret.strip(', \n\t')
  
  def convert(self, path):
    ''' 
    This method converts this Enum into a java code and returns as a tuple
    (<name of a class>, <java code>)
    '''
    if self.is_macro_enum():
      return self.convert_as_macro_enum(path)
    elif self.is_bitmask_enum():
      return self.convert_as_bitmask_enum(path)
    elif self.is_plain_enum():
      return self.convert_as_plain_enum(path)
    elif self.is_generative_enum_rnr():
      return self.convert_as_generative_rnr_enum(path)
    else:
      return self.convert_as_generative_enum(path)
    
  def convert_as_macro_enum(self, path):
    '''
    This function handles enum-based macro definition.
    '''  
    # template which is used to convert the macro enum.
    template = Template.get_template('tpl/macro_enum.tpl')
  
    packagename = self.spec.get_java_packagename(path)
    body = self.get_java_lines()
    result = template.substitute(
      {'packagename': packagename, 'typename':self.name, 'body':body}
    )
    return (self.name, result)
    
  def convert_as_bitmask_enum(self, path):
    template = Template.get_template('tpl/bitmask_enum.tpl')
    
    packagename = self.spec.get_java_packagename(path)
    rep = self.get_java_representation()
    methodname = self.spec.convert_to_camel(rep)
    body = self.get_java_lines('public static ' + rep)
    result = template.substitute(
        {'packagename': packagename, 'typename':self.name, 
         'body':body, 'rep':rep, 'methodname':methodname}                         
    )
    return (self.name, result)
    
  def convert_as_plain_enum(self, path):
    template = Template.get_template('tpl/enum.tpl')
    
    rep = self.get_java_representation()
    orep = Type.get_object_type(rep)
      
    packagename = self.spec.get_java_packagename(path)
    enumdef = self.get_java_lines()
    length = self.get_length()

    if rep == 'byte':
      bytegetname = ''
    else:
      bytegetname = self.spec.convert_to_camel(rep)
      
    result = template.substitute({
      'packagename':packagename, 'typename':self.name, 
      'type':rep, 'otype':orep,
      'enumdef':enumdef,'bytegetname':bytegetname, 'length':length
    })
    return (self.name, result)
   
  def convert_as_generative_enum(self, path):
    template = Template.get_template('tpl/gen_enum.tpl');
    
    rep = self.get_java_representation()
    orep = self.get_object_type(rep)
    packagename = self.spec.get_java_packagename(path)
    importname = packagename[:packagename.rfind('.')] + '.messages.*'
    supertype = self.get_java_supertype()
    enumdefs = self.get_java_generative_lines()
    length = self.get_length()
    if rep == 'byte':
      bytegetname = ''
    else:
      bytegetname = self.spec.convert_to_camel(rep)
      
    result = template.substitute({
      'packagename':packagename, 'importname':importname,
      'typename':self.name,'supertype':supertype,'enumdefs':enumdefs,
      'rep':rep, 'orep':orep, 'length':length, 'bytegetname':bytegetname
    })
    return (self.name, result)
     
  def convert_as_generative_rnr_enum(self, path):
    template = Template.get_template('tpl/gen_enum_rnr.tpl')
  
    rep = self.get_java_representation()             # internal representation type
    orep = self.get_object_type( rep )
    packagename = self.spec.get_java_packagename(path)
    importname = packagename[:packagename.rfind('.')] + '.messages.*'
    supertype = self.get_java_supertype()
    req_t = self.get_java_rnr_diff_type()
    req_v = self.get_java_rnr_diff_request_value()
    rep_v = self.get_java_rnr_diff_reply_value()
    length = self.get_length()
    if rep == 'byte':
      bytegetname = ''
    else:
      bytegetname = self.spec.convert_to_camel(rep)
  
    enumdefs = self.get_java_generative_rnr_lines()
    result = template.substitute({
      'packagename':packagename,'importname':importname,
      'typename':self.name,'supertype':supertype,'enumdefs':enumdefs,'rep':rep, 'orep':orep,
      'length':length, 'ctype':req_t, 'reqv':req_v, 'repv':rep_v,
      'bytegetname':bytegetname
    })
    return (self.name, result)
  
  
# enf of the Enum definition
  
def erat2():
    D = {  }
    yield 2
    for q in itertools.islice(itertools.count(3), 0, None, 2):
        p = D.pop(q, None)
        if p is None:
            D[q*q] = q
            yield q
        else:
            x = p + q
            while x in D or not (x&1):
                x += p
            D[x] = p
            
def get_primes(n):
  return list(itertools.takewhile(lambda p: p<n, erat2()))

class Struct(Type):
  
  primes = get_primes(3000)
  current_prime_index = 0
  
  def __init__(self, spec, definition):
    '''
    Constructor.
    '''
    Type.__init__(self)
    self.spec = spec
    self.name = definition['name']
    self.supertype = definition.get('supertype', None)
    self.body = definition['body']
    self.constructor = []
    self.align = int(definition['align'])
    
  def __repr__(self):
    return '<Struct name:%s, supertype:%s, body:%s>' % (self.name, self.supertype, self.body)
  
  def __getitem__(self, key):
    if key == 'spec': return self.spec
    elif key == 'name': return self.name
    elif key == 'supertype': return self.supertype
    elif key == 'body': return self.body
    elif key == 'constructor': return self.constructor
    else:
      return None

  def get_name(self):
    return self.name
      
  @staticmethod
  def get_primes(n):
    return list(itertools.takewhile(lambda p: p<n, erat2()))
  
  @staticmethod
  def get_next_prime():
    Struct.current_prime_index -= 1
    if abs(Struct.current_prime_index) > len(Struct.primes):
      return None
    else:
      return Struct.primes[Struct.current_prime_index]
  
  def is_topmost_struct(self):
    return self.supertype == None
  
  def get_supertype(self):
    '''
    This method returns a single supertype of this Struct object.
    '''
    if self.supertype:
      return self.spec.get_type(self.supertype)
    return None
  
  def get_supertypes(self):
    '''
    This method returns all supertypes of this Struct object.
    '''
    r = []
    c = self
    s = None
    while True:
      s = c.get_supertype()
      if s == None:
        break
      r.append(s)
      c = s
    r.reverse()
    return r
    
  def get_minimum_length(self):
    '''
    This method calculates the minimum length of the struct definition.
    '''
    ret = 0;
    tl = self.get_supertypes();     # get all supertypes
    if tl == None: tl = []
    tl.append(self)                 # append this to the end of it
    ret = 0
    for i in tl:
      for j in i.body:
        l = j['length']
        if l == None: 
#           print i.name, j
          if j['type'].startswith('OF'):
            itype = self.spec.get_type(j['type'])
#             if itype == None:
#               pprint.pprint( self.spec.type_dic )
            l = itype.get_minimum_length()
          else:
            l = 0
        ret += int(l)
    
    return ret    
    
  def has_field(self, name):
    supertype = self.get_supertype()
    if supertype and supertype.has_field(name):
      return True
    
    for i in self.body:
      if i.get('name', None):
        if i['name'] == name:
          return True
       
    return False
        
        
  def reduce(self):
    '''
    This method eliminates duplicate declarations within this 'sub'type.
    If assignment is found, the assignment is considered as a supertype method calling
    which should be applied within the constructor definition.
    Those assignments are saved within the self.constructor field (which is a list)
    '''
    parent = None
    supertypes = self.get_supertypes()
    
    if supertypes != None and len(supertypes) > 0:
      parent = supertypes[0]
      
    length = 0
    for i in supertypes:
      length += len(i.body)
      
    self.constructor.append('setLength(U16.t(MINIMUM_LENGTH));')
    for i in range(0, length):
      l = self.body.pop(0)
      if l.get('value', None):
        v = l['value']
        if v == '?': continue
        t = l['type']
        if t == 'pad': continue
        n = l['name']
        
        if parent != None and parent.has_field(n):
          prefixed_name = parent.name + self.spec.convert_to_camel(n)
        else:
          prefixed_name = ''
          
        ptype = self.spec.get_type(prefixed_name)
        ttype = self.spec.get_type(t)
        ntype = self.spec.get_type(self.spec.get_java_classname(n))

        if isinstance(ptype, Enum):
          jref = ptype.get_java_representation()
          self.constructor.append('set%s(%s.valueOf((%s)%s));' % (self.spec.convert_to_camel(n), ptype.name, jref, v))
        elif isinstance(ttype, Enum):
          jref = ttype.get_java_representation()
          if ttype.is_generative_enum_rnr():
            self.constructor.append('set%s(%s.valueOf((%s)%s, getType()));' % (self.spec.convert_to_camel(n), ttype.name, jref, v))
          else:
            self.constructor.append('set%s(%s.valueOf((%s)%s));' % (self.spec.convert_to_camel(n), ttype.name, jref, v))
        elif isinstance(ntype, Enum):
          jref = ntype.get_java_representation()
          if ntype.is_generative_enum_rnr():
            self.constructor.append('set%s(%s.valueOf((%s)%s, getType()));' % (self.spec.convert_to_camel(n), ntype.name, jref, v))
          else:
            self.constructor.append('set%s(%s.valueOf((%s)%s));' % (self.spec.convert_to_camel(n), ntype.name, jref, v))
        else:
          self.constructor.append('set%s((%s)%s);' % (self.spec.convert_to_camel(n), self.spec.convert_to_java_type(t), v))
    
     
  def process_pad_declaration(self, line, pad_num):
    '''
    This methods creates multiple pad variable declarations
    and returns a list of them.
    '''
    ret = []
    pad_sz = int(line['length'])
    div = 8
    while pad_sz > 0:
      p = pad_sz / div
      if p > 0:
        pad_num += 1
        n = 'pad_%sth' % pad_num
        # calculate java type name based on its size in the case of 'pad'
        ret.append('%s %s;' % (self.spec.convert_to_java_type(div), n))
        pad_sz -= div
      else:
        div = div >> 1
        
    return ret
  
  
  def get_struct_components(self):
    '''
    This method converts 'body' into variable declarations
    '''
    ret = {}          # return value. which is a map.
      
#     print name, definition
    
    # structures used for building the return value
    declarations = []
    constructor = self.constructor
    copyconstructor = []
    accessors = []
    hashcode_logics = []
    comparisons = []
    tostrings = []
    readfroms = []
    writetos = []
    imports = []
    computelengths = []
    
    if self.name == 'OFMessage':
      ret['implements'] = 'implements org.openflow.protocol.OFMessage'
      accessors.append('\tpublic byte getTypeByte() { return this.type.getTypeValue(); }\n')
    else:
      ret['implements'] = ''

    if self.supertype:
      copyconstructor.append("super(other);");
      constructor.insert(0, 'super();');
      readfroms.append('super.readFrom(data);')

    prev_var = None
    mark_added = False      
    prime = Struct.get_next_prime()
    pad_num = 0       # count the number of pad
    
    for i in self.body:
      '''
      each i is {'type':..., 'name':,..., 'value'('?'):..., 'length':... }
      '''
      if i['type'] == 'pad':
        # handles cases in that type is 'pad'
        pad_decls = self.process_pad_declaration(i, pad_num)
        declarations += pad_decls
        pad_num += len(pad_decls)
        
        for p in pad_decls:
          tl = p.split(' ');
          (ptype, pname) = (self.spec.convert_to_camel(tl[0].strip()), tl[1].strip(';'))
          if ptype == 'Byte':
            ptype = ''
          
          readfroms.append('this.%s = data.get%s();' % (pname, ptype))
          writetos.append("data.put%s(this.%s);" % (ptype, pname))
        
      else:
        # handles other type than 'pad'
        variable_type = i['type']
        variable_name = i['name']    
        variable_value = i.get('value', None)
        variable_length = i['length']
        
        prefixed_variable_name = ''.join([self.name, 
                                          self.spec.convert_to_camel(variable_name)])
        
        #
        # build declaration lines
        #
        
        # see if we can change the default java type name
        for ns in [prefixed_variable_name, variable_type, self.spec.get_java_classname(variable_name)]:
          tmp = self.spec.get_type(ns)
          if tmp:
            if isinstance(tmp, Enum) and tmp.is_bitmask_enum():
              continue
            else:
              variable_type = ns
              break
          
        if variable_type == 'List':
          declarations.append('%s<%s>  %s;' % ( variable_type, i['inner'], variable_name))
        else:
          declarations.append('%s  %s;' % ( variable_type, variable_name ))
        
        #
        # process imports
        #
        
        if variable_type.startswith('List'):  
          imports.append('import java.util.List;')
          imports.append('import java.util.LinkedList;')
        
        #
        # build copy constructor lines
        # 
        
        if variable_type == 'byte[]':
          copyconstructor.append('if (other.%s != null) { this.%s = java.util.Arrays.copyOf(other.%s, other.%s.length); }' 
                                   % (variable_name, variable_name, variable_name, variable_name))
        elif variable_type.startswith('OF'):
          # then, this type is either enum or struct.
          # in the case of struct, its enough to do new
          t = self.spec.get_type(variable_type)
          if isinstance(t,Enum):
            copyconstructor.append('this.%s = other.%s;' % (variable_name, variable_name))
          else:
            copyconstructor.append('this.%s = new %s(other.%s);' % (variable_name, variable_type, variable_name))
        elif variable_type.startswith('List'):
          inner = i['inner']
          copyconstructor.append('this.%s = (other.%s == null)? null: new Linked%s<%s>();' 
                                 % (variable_name, variable_name, variable_type, i['inner']))  
          copyconstructor.append('for ( %s i : other.%s ) { this.%s.add( new %s(i) ); }' 
                                 % (inner, variable_name, variable_name, inner))
        else:
          copyconstructor.append('this.%s = other.%s;' % (variable_name, variable_name))
        
        #
        # build constructor lines -- only for byte[], List, enumerations, and structs.
        #      
        
        if variable_value and variable_value != '?':
          constructor.append('this.%s = (%s)%s;' % (variable_name, variable_type, variable_value))
        elif variable_type == 'byte[]':
          if variable_length:
            constructor.append(''.join([variable_name, ' = new byte[', str(variable_length), '];']))
        elif variable_type.startswith('OF'):
          # then, this type is either enum or struct.
          # in the case of struct, its enough to do new
          t = self.spec.get_type(variable_type)
          if isinstance(t, Struct):
            constructor.append('this.%s = new %s();' % (variable_name, variable_type))              
        elif variable_type.startswith('List'):
          constructor.append('this.%s = new Linked%s<%s>();' % (variable_name, variable_type, i['inner']))  
          
        # 
        # now we build accessors 
        # 

        etype = self.spec.get_type(variable_type)
        if isinstance(etype, Enum) and etype.is_bitmask_enum():
          tpl = Template.get_template('tpl/accessor_bitmask_enum.tpl')
          return_type = etype.get_java_representation()
          method_name = self.spec.convert_to_camel(variable_name)
          accessor = tpl.safe_substitute({'class_name':self.name, 
                                          'return_type':return_type,
                                          'variable_type':variable_type,
                                          'variable_name':variable_name, 
                                          'method_name':method_name })
          accessors.append(accessor)
        else:
          if i.get('bitfields', None):
            tpl = Template.get_template('tpl/accessor_bitfield.tpl')
            bitfields = i['bitfields']
            return_type = variable_type
            type_size = self.spec.get_type_size( return_type )
            accu = 0;
            for bitfield in bitfields:
              method_name = self.spec.convert_to_camel(bitfield[0])
              shift_amount = type_size*8 - accu - int(bitfield[1])
              mask = '0b' + '1' * (type_size*8 - accu) + '0' * shift_amount; 
              accessor = tpl.safe_substitute({'class_name':self.name, 'return_type':return_type,
                                              'mask': mask,
                                              'variable_name':variable_name, 'method_name':method_name,
                                              'shift_amount':shift_amount})
              accessors.append(accessor)
              accu = accu + int(bitfield[1])
          else:
            tpl = Template.get_template('tpl/accessor_primitive_type.tpl')
            return_type = variable_type
            if return_type == 'List':
              return_type = 'List<%s>' % i['inner']
            method_name = self.spec.convert_to_camel(variable_name)
            accessor = tpl.safe_substitute({'class_name':self.name, 
                                            'return_type':return_type,
                                            'variable_name':variable_name, 
                                            'method_name':method_name })
            accessors.append(accessor)
          
            if self.name == 'OFMessage' and Type.is_primitive_java_type(variable_type) and Type.has_longer_java_type(variable_type):
              longer_type = Type.get_longer_java_type(variable_type)
              longer_type_util = Type.get_java_util_type(variable_type)
              tpl = Template.get_template('tpl/accessor_primitive_type_larger.tpl')
              accessor = tpl.safe_substitute({'class_name':self.name, 
                                              'variable_name':variable_name,
                                              'method_name':method_name, 
                                              'longer_type':longer_type,
                                              'longer_type_util':longer_type_util})
              accessors.append(accessor)
                    
        #
        # hashcode
        # 
        if Type.is_value_java_type(variable_type):
          hashcode_line = 'result = prime * result + (int) %s;' % variable_name
        elif variable_type == 'byte[]':
          hashcode_line = 'result = prime * result + ((%s == null)?0:java.util.Arrays.hashCode(%s));' % (variable_name, variable_name)
        else:
          hashcode_line = 'result = prime * result + ((%s == null)?0:%s.hashCode());' % (variable_name, variable_name)
          
        hashcode_logics.append(hashcode_line)
        
        #
        # equals
        #
        if Type.is_value_java_type(variable_type):
          comparison = 'if ( %s != other.%s ) return false;' % (variable_name, variable_name)
        else:
          comparison = 'if ( %s == null && other.%s != null ) { return false; }' % (variable_name, variable_name)
          comparisons.append(comparison)
          if variable_type == 'byte[]':
            comparison = 'else if ( !java.util.Arrays.equals(%s, other.%s) ) { return false; }' %(variable_name, variable_name)
          else:
            comparison = 'else if ( !%s.equals(other.%s) ) { return false; }' % (variable_name, variable_name)
          
        comparisons.append(comparison)
        
        #
        # computelength
        # 
        if variable_type.startswith('List'):
          inner = i['inner']
          itype = self.spec.get_type(inner)
          if itype == None:
            type_size = self.spec.get_type_size(inner)
            clen = 'if ( this.%s != null ) { len += %s * this.%s.size(); }' % (variable_name, type_size, variable_name)
          else:
            clen = 'for ( %s i : this.%s ) { len += i.computeLength(); }' % (inner, variable_name)
          computelengths.append(clen);
        elif variable_type == 'byte[]':
          if not variable_length:
            computelengths.append('if ( this.%s != null ) { len += this.%s.length; } ' % (variable_name, variable_name))
        elif variable_type.startswith('OFMatch') and variable_type != 'OFMatchType' and self.spec.get_version() == '1.3':
          computelengths.append('len += %s.lengthDiff();' % variable_name)
        
        #
        # toString
        #
        if Type.is_value_java_type(variable_type):
          tsline = '":%s=" + %s.f(%s) + ' % (variable_name, Type.get_java_util_type(variable_type), variable_name)
        elif variable_type == 'byte[]':
          tsline = '":%s=" + java.util.Arrays.toString(%s) + ' % (variable_name, variable_name)
        else:
          tsline = '":%s=" + %s.toString() + ' % (variable_name, variable_name)
        tostrings.append(tsline)
        
        #
        # readfrom
        #
        
        if Type.is_value_java_type(variable_type):
          if variable_type == 'byte':
            rline = 'this.%s = data.get();' % variable_name
          else:
            rline = 'this.%s = data.get%s();' % (variable_name, self.spec.convert_to_camel(variable_type))
        elif variable_type == 'byte[]':
          if self.name == 'OFOxm' and prev_var and prev_var.endswith("length"):
            rline = 'if ( this.%s > 0 ) {' % prev_var
            readfroms.append(rline)
            rline = '\tif ( this.%s == null ) this.%s = new byte[this.%s];' % (variable_name, variable_name, prev_var)
            readfroms.append(rline)
            rline = '\tdata.get(this.%s);' % variable_name
            readfroms.append(rline)
            rline = '}'
          else:
#           sz =  self.spec.get_type_size(variable_type)
            sz = variable_length
            if sz == None or sz == 0:
              sz =  self.spec.get_type_size(variable_type)
            if sz == None or sz == 0:
              if not mark_added:
                readfroms.insert(0, 'int mark = data.position();')
                mark_added = True
              sz = '(getLength() - (data.position() - mark))'
            rline = 'if ( this.%s == null ) this.%s = new byte[%s];' % (variable_name, variable_name, sz)
            readfroms.append(rline)
            rline = 'data.get(this.%s);' % variable_name
        elif variable_type == ('String'):
#           limit = 256
#           if variable_name.find('serial') >= 0 :
#             limit = 32
          rline = 'this.%s = StringByteSerializer.readFrom(data, %d);' % (variable_name, variable_length)
        elif isinstance(self.spec.get_type(variable_type), Enum):
          etype = self.spec.get_type(variable_type)
          if etype.is_bitmask_enum():
            rline = 'if (this.%s == null) this.%s = new %s();' % (variable_name, variable_name, variable_type)
            readfroms.append(rline)
            rline = 'this.%s.setValue( %s.readFrom(data) );' % (variable_name, variable_type)
          elif etype.is_generative_enum_rnr():
            rline = 'this.%s = %s.valueOf(%s.readFrom(data), super.getType());' % (variable_name, variable_type, variable_type)
          else:
            rline = 'this.%s = %s.valueOf(%s.readFrom(data));' % (variable_name, variable_type, variable_type)
        else:
          if variable_type.startswith('List'):
            inner = i['inner']
            itype = self.spec.get_type(inner)
            iitype = self.spec.get_type(inner + 'Type')
            rline = 'if (this.%s == null) this.%s = new Linked%s<%s>();' % (variable_name, variable_name, variable_type, inner)
            readfroms.append(rline)
            if isinstance(itype, Enum) and itype.is_generative_enum():
              if not mark_added:
                readfroms.insert(0, 'int mark = data.position();')
                mark_added = True
              rline = '%sType.parse(this.%s, data, (int)getLength() - (data.position() - mark));' % (inner, variable_name)
            else:
              if prev_var and prev_var.endswith("_length"):
                if isinstance(iitype, Enum) and iitype.is_generative_enum():
                  rline = 'for (int i = 0; i < this.%s; ) {' % prev_var
                  readfroms.append(rline)
                  rline = '  data.mark();'
                  readfroms.append(rline)
                  rline = '  short __t = data.getShort();'
                  readfroms.append(rline)
                  rline = '  data.reset();'
                  readfroms.append(rline)
                  rline = '  %s t = %s.valueOf(__t).newInstance();' % (itype.name, iitype.name)
                  readfroms.append(rline)
                  rline = '  t.readFrom(data);'
                  readfroms.append(rline)
                  rline = '  this.%s.add(t);' % variable_name
                  readfroms.append(rline)
                  if itype.has_field('length'):
                    rline = '  i += t.getLength();'
                  else:
                    rline = '  i += %s.MINIMUM_LENGTH();' % itype.name
                  readfroms.append(rline)
                  rline = '}'
                elif itype == None:
                  # this case is for the primitive object type is used for inner.
                  method_name = inner
                  if method_name == 'Byte': method_name = ''
                  elif method_name == 'Integer': method_name = 'Int'
                  type_len = self.spec.get_type_size(inner)
                  rline = 'for (int i = 0; i < this.%s; ) { this.%s.add( data.get%s() ); i += %s; } ' % (prev_var, method_name, type_len)
                else:
                  # all the other case.
                  if isinstance(itype, Struct) and itype.has_field('length'):
                    rline = 'for (int i = 0; i < this.%s; ) { %s t = new %s(); t.readFrom(data); this.%s.add(t); i += t.getLength(); }' % (prev_var, inner, inner, variable_name)
                  else:
                    rline = 'for (int i = 0; i < this.%s; ) { %s t = new %s(); t.readFrom(data); this.%s.add(t); %s.MINIMUM_LENGTH; }' % (prev_var, inner, inner, variable_name, inner)
              else:
                if not mark_added:
                  readfroms.insert(0, 'int mark = data.position();')
                rline = 'int __cnt = ((int)getLength() - (data.position() - mark));'
                readfroms.append(rline)
                if isinstance(iitype, Enum) and iitype.is_generative_enum():
                  rline = 'while (__cnt > 0) {'
                  readfroms.append(rline)
                  rline = '  data.mark();'
                  readfroms.append(rline)
                  rline = '  short __t = data.getShort();'
                  readfroms.append(rline)
                  rline = '  data.reset();'
                  readfroms.append(rline)
                  rline = '  %s t = %s.valueOf(__t).newInstance();' % (itype.name, iitype.name)
                  readfroms.append(rline)
                  if itype.has_field('length'):
                    rline = '  t.readFrom(data); __cnt -= t.getLength();'
                  else:
                    rline = '  t.readFrom(data); __cnt -= %s.MINIMUM_LENGTH;' % itype.name
                  readfroms.append(rline)
                  rline = '  this.%s.add(t);' % variable_name
                  readfroms.append(rline)
                  rline = '}'
                elif itype == None:
                 # this case is for the primitive object type is used for inner.
                  method_name = inner
                  if method_name == 'Byte': method_name = ''
                  elif method_name == 'Integer': method_name = 'Int'
                  type_len = self.spec.get_type_size(inner)
                  rline = 'while (__cnt > 0) { this.%s.add( data.get%s() ); __cnt -= %s; } ' % (variable_name, method_name, type_len) 
                else:
                  if isinstance(itype, Struct) and itype.has_field('length'):
                    rline = 'while (__cnt > 0) { %s t = new %s(); t.readFrom(data); this.%s.add(t); __cnt -= t.getLength(); }' % (inner, inner, variable_name)
                  else:
                    if inner == 'OFOxm':
                      rline = 'while (__cnt > 0) { %s t = new %s(); t.readFrom(data); this.%s.add(t); __cnt -= (%s.MINIMUM_LENGTH + t.getPayloadLength()); }' % (inner, inner, variable_name, inner)
                    else:
                      rline = 'while (__cnt > 0) { %s t = new %s(); t.readFrom(data); this.%s.add(t); __cnt -= %s.MINIMUM_LENGTH; }' % (inner, inner, variable_name, inner)
          else:
            rline = 'if (this.%s == null) this.%s = new %s();' % (variable_name, variable_name, variable_type)
            readfroms.append(rline)
            rline = 'this.%s.readFrom(data);' % variable_name
        readfroms.append(rline)
        
        #
        # writeto
        #
        if Type.is_value_java_type(variable_type):
          if variable_type == 'byte':
            rline = 'data.put(this.%s);' % variable_name
          else:
            rline = 'data.put%s(this.%s);' % (self.spec.convert_to_camel(variable_type), variable_name)
        elif variable_type == 'byte[]':
          rline = 'if ( this.%s != null ) { data.put(this.%s); }' % (variable_name, variable_name)
        elif variable_type == 'String':
#           limit = 256
#           if variable_name.find('serial') >= 0:
#             limit = 32
          rline = 'StringByteSerializer.writeTo(data, %d, %s);' % (variable_length, variable_name)
        elif isinstance(self.spec.get_type(variable_type), Enum):
          etype = self.spec.get_type(variable_type)
          mn = self.spec.convert_to_camel(etype.get_java_representation())
          if mn == 'Byte': mn = ''
          if etype.is_generative_enum():
            rline = 'data.put%s(this.%s.getTypeValue());' % (mn, variable_name)
          else:
            rline = 'data.put%s(this.%s.getValue());' % (mn, variable_name)
        else:
          if variable_type.startswith('List'):
            inner = i['inner']
            itype = self.spec.get_type(inner)
            if isinstance(itype,Enum) and itype.is_generative_enum():
              rline = 'if (this.%s != null) %sType.write(this.%s, data);' % (variable_name, inner, variable_name)
            elif itype == None:
              method_name = inner;
              if method_name == 'Byte': method_name = ''
              elif method_name == 'Integer': method_name = 'Int'
              rline = 'if ( this.%s != null ) for (%s t: this.%s) { data.put%s(t); }' % (variable_name, inner, variable_name, method_name)
            else:
              rline = 'if (this.%s != null ) for (%s t: this.%s) { t.writeTo(data); }' % (variable_name, inner, variable_name)
          else:
            rline = '%s.writeTo(data);' % variable_name
        writetos.append(rline)
        
        prev_var = variable_name
        
    # end of for 
    
    # add readfrom lines if there is alignment considerations such as 
    # align(8) at the end of struct.
    if self.align > 0: 
        readfroms.append('int __align = alignment(getLength(), %d);' % self.align)
        readfroms.append('for (int i = 0; i < __align; ++i ) { data.get(); }')
        
    # add writeto lines if there is alignment considerations such as 
    # align(8) at the end of struct.
    if self.align > 0:
        writetos.append('int __align = alignment(computeLength(), %d);' % self.align)
        writetos.append('for (int i = 0; i < __align; ++i ) { data.put((byte)0); }')
    
    # build hashcode lines
    htpl = Template.get_template('tpl/struct_hashcode.tpl')
    prime_logics = '\n\t\t'.join(hashcode_logics)
    hashcode = htpl.safe_substitute({'prime':prime, 
                                     'prime_logics':prime_logics})
    
    # build equals lines
    ctpl = Template.get_template('tpl/struct_equals.tpl')
    if len(comparisons) > 0:
      comparisons.insert(0, '%s other = (%s) obj;' % (self.name, self.name))
    equal_logics = '\n\t\t'.join(comparisons)
    equals = ctpl.safe_substitute({'classname':self.name, 'equal_logics':equal_logics})
    
    #build tostring lines
    tos = '\n\t\t'.join(tostrings).rstrip(' +')
    if not self.is_topmost_struct():
      ppix = 'super.toString() + '
    else:
      ppix = ''
      
    if len(tos) > 0 :
      tostring = (('return %s ":'%ppix)) + self.name + '-"+' + tos + ';'
    else:
      tostring = (('return %s ":'%ppix)) + self.name + '";'
    
    #build readfrom lines
    readfrom = '\n\t\t'.join(readfroms)
    
    #build writeto lines
    writeto = '\n\t\t'.join(writetos)
    
    #build computelength
    computelength = '\n\t\t'.join(computelengths)
        
    ret['declarations'] = '\n\t'.join(declarations)
    ret['constructor'] = '\n\t\t'.join(constructor)
    ret['accessors'] = '\n'.join(accessors)
    ret['hashcode'] = hashcode
    ret['equals'] = equals
    ret['tostring'] = tostring
    ret['readfrom'] = readfrom
    ret['writeto'] = writeto
    ret['imports'] = imports
    ret['computelength'] = computelength
    ret['copyconstructor'] = '\n\t\t'.join(copyconstructor)
    
    return ret
    
  
  def convert(self, path):
    '''
    This function processes struct and creates Java code for each.
    '''
    if not self.is_topmost_struct():              # there's supertype. 
      superdef = self.get_supertype()           # superclass definition
      supername = superdef.name                   # superclass name
      inherit_method = 'extends'
    else:
      superdef = None
      supername = ''
      inherit_method = ''
      
    superwriteto = ''
    
    # if supertype is found, reduce the subtype implementation
    if superdef:
      self.reduce()
      superwriteto = 'super.writeTo(data);'
      
    template = Template.get_template('tpl/struct.tpl')
 
    packagename = self.spec.get_java_packagename(path)
    typename = self.name
    importname = 'import ' + packagename[:packagename.rfind('.')] + '.types.*;'
    
    minimumlength = self.get_minimum_length()
    component_map = self.get_struct_components()
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
      'computelength':component_map['computelength'],
      'align': self.align
    })
    return (self.name, result)
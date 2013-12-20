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
      
    interface_converter = openflow_converter.Converter.create_interface_converter(specs)
    interface_converter.convert()
    
    for spec in specs:
      concreate_converter = openflow_converter.Converter.create_concrete_converter(spec)
      concreate_converter.convert(interface_converter)
      
except:
  traceback.print_exc()


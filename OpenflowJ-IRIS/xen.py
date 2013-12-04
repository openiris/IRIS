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
    for file in flist: 
      sp = spec.Spec(file)
      sp.load_spec()
      converter = openflow_converter.Converter.create_converter(sp)
      converter.convert()
except:
  traceback.print_exc()


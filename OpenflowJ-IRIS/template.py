
import string

class Template:
  
  templates = {}
  
  @staticmethod
  def get_template(path):
    ''' 
    load template and return the template as string.
    '''
    if Template.templates.get(path, None):
      return Template.templates[path]
    r = string.Template(open(path, 'r').read())
    Template.templates[path] = r
    return r
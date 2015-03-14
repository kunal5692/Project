import webapp2
import json

class MainHandler(webapp2.RequestHandler):
   def get(self):
      self.response.headers['Content-Type'] = 'text/plain'
      self.response.out.write("server running")
	
   def post(self):
   	inp = self.request.body
	try:
           out = json.loads(inp)
	except ValueError:
           self.response.headers['Content-Type'] = 'text/plain'
           self.response.set_status( 500,"Internal server error" ) 
           self.response.out.write( 'Invalid JSON object in request: '+inp )
           #logging.error( 'Invalid JSON object: '+inp )
           return
	for x in out:
	    content = x['content']
	    #content = content.decode("utf-8")
	    #message = x[message]
	    #resultElement = {'content':content}
	    #recvlist.append(resultElement)
	self.response.headers['Content-Type'] = 'text/vnd.aexp.json.resp'
        self.response.set_status( 200,"OK" )
	self.response.out.write(json.dumps(content))
		
app = webapp2.WSGIApplication([('/',MainHandler)],debug=True)



		

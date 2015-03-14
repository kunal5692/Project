import webapp2
import os
import jinja2

class MainHandler(webapp2.RequestHandler):
	def get(self):
		self.response.write("server running")
		
	def post(self):
		try:
            jsonstring = self.request.body('content')
		except ValueError:
            self.response.headers['Content-Type'] = 'text/plain'
            self.response.set_status( 500,"Internal server error" ) 
            self.response.out.write( 'Invalid JSON object in request: '+jsonstring )
            logging.error( 'Invalid JSON object: '+jsonstring )
            return
		
		poststring = json.loads(jsonstring)
		self.response.headers['Content-Type'] = 'text/vnd.aexp.json.resp'
        self.response.set_status( 200,"OK" )
		self.response.write(json.dumps(recvdstring))
				
application = webapp2.WSGIApplication([('/',MainHandler)],debug=True)


def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()


		

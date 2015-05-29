import webapp2
import json
import MySQLdb

_INSTANCE_NAME = 'lamp-test212:location-instance'

class MainHandler(webapp2.RequestHandler):
   def get(self):
      self.response.headers['Content-Type'] = 'text/plain'
      self.response.out.write("Server Running")
      db = MySQLdb.connect(unix_socket='/cloudsql/'+_INSTANCE_NAME ,db='location', user='root', passwd='mayur')
      cursor = db.cursor()

      cursor.execute("INSERT INTO navy (router1, router2, router3, details) VALUES (%s, %s, %s, 'Sample2')", (RSS1, RSS2, RSS3)x)
      cursor.execute("SELECT details FROM navy")
      results = cursor.fetchall()
      db.commit()
      db.close()
      self.response.out.write(results)


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
	
      
	responseList=[]
	#for x in out:
	RSS1 = out['rss1']
	RSS2 = out['rss2']
	RSS3 = out['rss3']
	responseList.append(RSS1)
	responseList.append(RSS2)
	responseList.append(RSS3)

      	
      	
	#content = content.decode("utf-8")
	#message = x[message]
	#resultElement = {'content':content}
	#recvlist.append(resultElement)
	self.response.headers['Content-Type'] = 'text/vnd.aexp.json.resp'
        self.response.set_status( 200,"OK" )
	self.response.out.write(json.dumps(responseList))
	#self.response.out.write(json.dumps("," + RSS2))
	#self.response.out.write(json.dumps("," + RSS3))
		
app = webapp2.WSGIApplication([('/',MainHandler)],debug=True)

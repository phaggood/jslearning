import groovy.json.*

// use this to invalidate a session

response.setContentType('application/json')

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

def statusMsg = 'logged out'

// remove session if exists
def session = request.getSession(true);
if (session) {
    session.invalidate()
}

// build response
jsonBuilder.status {
    message statusMsg
}

print jsonWriter.toString()
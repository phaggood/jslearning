import groovy.json.*

def gData = new GData();

response.setContentType('application/json')
def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

def userId = params.userId
def sessionId = params.sessionId

def usr
if (userId) {
    usr = gData.getUserByUserId(userId)
} else {
    usr = gData.getUserBySessionId(sessionId)
}

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" sessionId
        "Date" new Date().format("dd.MM.yyyy HH:mm")
    }


    Data (
         ({
            firstname usr.firstname
            lastname usr.lastname
            id usr.userid
        })
    )

}


print jsonWriter.toString()

// Check query parameters callback or jsonp (just wanted to show off
// the Elvis operator - so we have two query parameters)
//def jsonp = params.callback ?: params.jsonp
//if (jsonp) print jsonp + '('
//    jsonOutput.write(out)
//if (jsonp) print ')'
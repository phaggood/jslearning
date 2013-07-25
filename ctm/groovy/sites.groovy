import groovy.json.*

def gData = new GData();

response.setContentType('application/json')

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

def sessionId = params.sessionId
def sites = gData.getSites(sessionId)

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" getSessionID()
        "Date" new Date().format("dd.MM.yyyy HH:mm")
    }

    Data (

        sites.each ({ site->
            "id" site.id
            "name" site.name
            "desc" site.description
            "type" site.type
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
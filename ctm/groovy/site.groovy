import groovy.json.*
evaluate(new File("./groovy/GroovyData.groovy"))

response.setContentType('application/json')

def siteId = params.siteId
def sessionId = params.sessionId
def site = getSite(siteId)

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" getSessionID()
        "Date" new Date().format("dd.MM.yyyy HH:mm")
    }

    Data (

        site {
            "id" site.id
            "name" site.name
            "desc" site.description
            "type" site.type
        }
    )
}


print jsonWriter.toString()

// Check query parameters callback or jsonp (just wanted to show off
// the Elvis operator - so we have two query parameters)
//def jsonp = params.callback ?: params.jsonp
//if (jsonp) print jsonp + '('
//    jsonOutput.write(out)
//if (jsonp) print ')'
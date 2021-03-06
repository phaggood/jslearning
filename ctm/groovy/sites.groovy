import groovy.json.*

def gData = new GData();

response.setContentType('application/json')

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)
def siteId = params.siteId
def sessionId = params.sessionId

def sites = []

if (siteId) {
    sites << gData.getSite(siteId)
}  else {
    sites = gData.getSitesBySession(sessionId)
}

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" sessionId
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

// return json result
print jsonWriter.toString()

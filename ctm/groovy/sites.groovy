import groovy.json.*

def gData = new GData();

response.setContentType('application/json')

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)
def siteId = params.siteId
def sessionId = params.sessionId
def statusCode = "200"
def sites = []

if (siteId) {
    sites << gData.getSite(siteId)
}  else {
    sites = gData.getSitesBySession(sessionId)
}

def toolCount(siteId) {
    return gData.getToolCount(siteId)
}

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" sessionId
        "Date" new Date().format("dd.MM.yyyy HH:mm")
    }

    Sites (
        sites
    )
    Count (
      sites.size()
     )
}

// return json result
print jsonWriter.toString()

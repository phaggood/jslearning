import groovy.json.*

def gData = new GData();

response.setContentType('application/json')

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)
def siteId = params.siteId
def sessionId = params.sessionId
def toolId = params.toolId

def tools = []

if (toolId) {
    tools << gData.getTool(toolId)
} else if (siteId) {
    tools = gData.getSiteTools(siteId)
}

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" sessionId
        "Date" new Date().format("dd.MM.yyyy HH:mm")
    }

    Data (

        tools.each ({ tool ->
            "id" tool.id
            "name" tool.title
            "desc" tool.description
        })
    )
}
// return json result
print jsonWriter.toString()

import groovy.json.*

response.setContentType('application/json')

def getSessionID() {
    def sess = '1234'
}


// define array of dummy sites; this will be replaced with direct call to CTools for a sitelist
// i.e. def sites = getURL(ctools_url+'/sites?sessionID=${getSessionID()}')
def sites = []
sites << [id: 1, name: 'Site001', description: 'Accounting'   , type:'course']
sites << [id: 2, name: 'Site002', description: 'Astronomy'    , type:'course']
sites << [id: 3, name: 'Site003', description: 'Research 001' , type:'project']
sites << [id: 4, name: 'Site004', description: 'Research 002' , type:'project']
sites << [id: 5, name: 'Site005', description: 'Portfolio 001', type:'portfolio']

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

//jsonBuilder.sites {
//    sitelist sites
//    sitecount sites.size()
//}


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
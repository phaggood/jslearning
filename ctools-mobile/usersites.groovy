import groovy.json.*

response.setContentType('application/json')

def usersites = []

usersites << [user: 'user001', sites: {'Site001', 'Site002'}]
usersites << [user: 'user002', sites: {'Site002'}]
usersites << [user: 'user003', sites: {'Site003', 'Site004'}]

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

def userid = params.userid

if (userid) {
    jsonBuilder.usersites {
        sites usersites.findByUser(userid)
        sitecount usersites.findByUser()?.size()
    }
} else {
    jsonBuilder.usersites {
        sites []
        sitecount 0
    }
}

print jsonWriter.toString()
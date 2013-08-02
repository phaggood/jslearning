import groovy.json.*


response.setContentType('application/json')


def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

def gData = new GData();
// get request params
def username = params.username
def password = params.password
def login = false

// attempt to match username and pwd from list of users, return valid session and status = 200 if found
def result = gData.login(username, password)

if (result.sessionId == null) {
    status = "401"
}  else {
    status = "200"
}

def root = jsonBuilder {
    Header {
        "Typ" "update"
        "Code" "UTF-8"
        "SessionID" result.sessionId
        "Date" new Date().format("dd.MM.yyyy HH:mm")
    }

    Data (
         ({
            status      result.statusMsg
            sessionId   result.sessionId
            status      status
        })
    )

}

print jsonWriter.toString()
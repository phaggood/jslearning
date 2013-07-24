import groovy.json.*

// use this to simulate a table of users
def users = []
users <<  [username:'user001', password:'user001', name:'User 001']
users <<  [username:'user002', password:'user002', name:'User 002']
users <<  [username:'user003', password:'user003', name:'User 003']
users <<  [username:'user004', password:'user004', name:'User 004']

response.setContentType('application/json')

// store username and generated dummy sessionID in session
def createSession(usrname) {
    if (!session) {
        session = request.getSession(true)
    }

    if (!session.user) {
        session.user = usrname
        session.sessionID = "sess_${usrname}"
    }

    return session.sessionID
}

def jsonWriter = new StringWriter()
def jsonBuilder = new StreamingJsonBuilder(jsonWriter)

// get request params
def username = params.username
def password = params.password
def login = false
def currentUser = ''
def statusMsg = 'missing username'

// attempt to match username and pwd from list of users
if (username) {
    def user = users.find{ usr -> usr.username == username}
    if (user) {
        if (password) {
            if (user.password == password) {
                login = true
                currentUser = user.name
                statusMsg = "success"
            }
            else {
                statusMsg = 'bad password'
            }
        } else {
            statusMsg = 'missing password'
        }
     }
     else {
        statusMsg = 'user not found'
     }
}

jsonBuilder.status {
    message statusMsg
}

if (login) {
    jsonBuilder.user {
        name  currentUser
        session createSession(username)
    }
}

print jsonWriter.toString()

sites = [
        s001:[name: 'Site001', description: 'Accounting', stype:'course'],
        s002:[name: 'Site002', description: 'Pottery', stype:'course'],
        s003:[name: 'Site003', description: 'Astronomy', stype:'course'],
        s004:[name: 'Site004', description: 'Engineering', stype:'course']
        ]


users = [
                bjones :[password:'pword', fname: 'Bob' , lname: 'Jones', sessId:'s001', usersites:['s001', 's002']       ],
                jdoe   :[password:'pword', fname: 'Jane', lname: 'Doe',   sessId:'s002', usersites:['s001', 's002', 's004'] ],
                mbill  :[password:'pword', fname: 'Mr'  , lname: 'Bill',  sessId:'s004', usersites:['s003','s004']             ]
            ]


tools = [
                t1  :[title:'Home'         , description: 'Main tool'           , sites:['s001', 's002']       ],
                t2  :[title:'Announcements', description: 'System Announcements', sites:['s001', 's002', 's004'] ],
                t3  :[title:'Gradebook'    , description: 'Grades'              , sites:['s004']             ],
                t4  :[title:'Chat'         , description: 'Chat server'         , sites:['s004']             ],
                t5  :[title:'Resources'    , description: 'File Resources'      , sites:['s004']             ],
                t6  :[title:'Gradebook2'   , description: 'Student Grades'      , sites:['s004']             ],
                t7  :[title:'Forums'       , description: 'Site Forums'         , sites:['s004']             ]
]

siteTools = [
        s001:['t1', 't2', 't3', 't4'],
        s002:['t1', 't3', 't4', 't5'],
        s003:['t1', 't3', 't6','t7'],
        s004:['t1', 't5', 't7']
]

// find user in map via key and password value, return valid session id
def login(String username, String pword) {
    def result = [:]
    def sessionId
    def statusMsg = "success"
    if (username) {
        def user = users.get(username)
        if (user == null) {
            statusMsg = "Username not found"
        } else {
            if (user.password != pword) {
                sessionId = null
                statusMsg = "- Bad password ${pword}"
            } else {
                sessionId = user.sessId;
            }
        }
    } else {
        statusMsg = "Empty username"
    }
    result << [sessionId:sessionId]
    result << [statusMsg:statusMsg]
    return result;
}

def getUserBySessionId(String sessId) {
    def user = [:]
    users.each{ k,v->
        if (v.sessId == sessId) {
            user << [fname: v.fname, lname:v.lname, id:k]
        }
    }
    return user
}

def getUserByUserId(String userid) {
    def result = [:]
    def user = users.get(userid) // userid is same as username here
    if (user) {
       result << [firstname:user.fname, lastname:user.lname, userid:userid]
    }
    return result
}

// get all sites valid by session id
def getSites(String sessId) {
    def siteList = []
    users.each{ k,v->
        if (v.sessId == sessId) {
            v.sites.each{ sitekey ->
                println "sitekey ${sitekey}"
                siteList << getSite(sitekey)
            }
        }
    }
    return siteList
}

// get site by siteId
def getSite(String siteId) {
    def result = [:]
    def site = sites.get(siteId)
    if (site) {
        result << [name:site.name, id:"site-${siteId}", description:site.description, type:site.stype]
    }
    return result
}

def getTool(String toolId) {
    def result = [:]
    def tool = tools.get(toolId)
    if (tool) {
        result << [id:toolId, title:tool.title, description:tool.description]
    }
    return result
}

def getSiteTools(String siteId) {
    def result = []
    def tools = siteTools.get(siteId)
    if (tools) {
        print "tools ${tools}"
        tools.each{ toolId ->
            result << getTool(toolId)

        }
    }
    return result

}

/*def sessId = login('jdoe','pword')
println "session ${sessId}"
def user = getUserBySessionId(sessId)
println "user ${user}"
def sitelist = getSites(sessId)
println "sites ${sitelist.toString()}"
def sesssite = getSite(sitelist[0].id)
println "Site ${sesssite}"
def sitetoollist = getSiteTools(sitelist[0].id)
println sitetoollist.toString()
//def getTool(sitetoollist(0))
*/

def uname= 'mbill'
def pass = 'pword'
def session = login(uname, pass);
println "Session ${session}"

def usr = getUserBySessionId(session.sessionId)
println "User ${usr}"
println "By id : ${getUserByUserId(usr.id)}"

println "Sites for session ${getSites(session.sessionId)}"

//def sitelist = getSites("s001")
//println "sites ${sitelist.toString()}"


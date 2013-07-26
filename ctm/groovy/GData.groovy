public class GData {


 def sites = [
        s001:[name: 'Site001', description: 'Accounting', stype:'course'],
        s002:[name: 'Site002', description: 'Pottery', stype:'course'],
        s003:[name: 'Site003', description: 'Astronomy', stype:'course'],
        s004:[name: 'Site004', description: 'Engineering', stype:'course']
        ]


 def users = [
                bjones :[password:'pword', fname: 'Bob' , lname: 'Jones', sessId:'ss001', sites:['s001', 's002']       ],
                jdoe   :[password:'pword', fname: 'Jane', lname: 'Doe',   sessId:'ss002', sites:['s001', 's002', 's004'] ],
                tdoc   :[password:'pword', fname: 'Doctor', lname: 'Who', sessId:'ss003', sites:['s001', 's004'] ],
                mbill  :[password:'pword', fname: 'Mr'  , lname: 'Bill',  sessId:'ss004', sites:['s003','s004']             ]
            ]


 def tools = [
                t1  :[title:'Home'         , description: 'Main tool'           , sites:['s001', 's002']       ],
                t2  :[title:'Announcements', description: 'System Announcements', sites:['s001', 's002', 's004'] ],
                t3  :[title:'Gradebook'    , description: 'Grades'              , sites:['s004']             ],
                t4  :[title:'Chat'         , description: 'Chat server'         , sites:['s004']             ],
                t5  :[title:'Resources'    , description: 'File Resources'      , sites:['s004']             ],
                t6  :[title:'Gradebook2'   , description: 'Student Grades'      , sites:['s004']             ],
                t7  :[title:'Forums'       , description: 'Site Forums'         , sites:['s004']             ]
]

 def siteTools = [
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
    def result = [:]
    users.each{ k,v->
        if (v.sessId == sessId) {
            result << [firstname:v.fname, v:user.lname, id:k, sites:v.sites]
            return result
        }
    }
    return result
}

def getUserByUserId(String userid) {
    def result = [:]
    def user = users.get(userid) // userid is same as username here
    if (user) {
       result << [firstname:user.fname, lastname:user.lname, userid:userid]
    }
    return result
}

def getSitesBySessionId(String sessId){
    def result = []
    def user = getUserBySessionId(sessId)
    if (user) {
        def sitelist = user.sites
        sitelist.each{ usrsiteId ->
            result << getSite(usrsiteId)
        }
    }
    return result
}


// get site by siteId
def getSite(String siteId) {
    def result = [:]
    def site = sites.get(siteId)
    if (site) {
        result << [name:site.name, id:"${siteId}", description:site.description, type:site.stype]
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
        tools.each{ toolId ->
            result << getTool(toolId)

        }
    }
    return result

}
}
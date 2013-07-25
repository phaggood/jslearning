public static class GroovyData {

def sites = [
        s1:[id:'001',name: 'Site001', description: 'Accounting', stype:'course'],
        s2:[id:'002', name: 'Site002', description: 'Pottery', stype:'course'],
        s3:[id:'003',name: 'Site003', description: 'Astronomy', stype:'course'],
        s4:[id:'004',name: 'Site004', description: 'Engineering', stype:'course']
        ]


def users = [
                bjones :[password:'pword', fname: 'Bob' , lname: 'Jones', sessId:'s001', sites:['s1', 's2']       ],
                jdoe   :[password:'pword', fname: 'Jane', lname: 'Doe',   sessId:'s002', sites:['s1', 's2', 's4'] ],
                mbill  :[password:'pword', fname: 'Mr'  , lname: 'Bill',  sessId:'s004', sites:['s4']             ]
            ]


def tools = [
                t1  :[title:'Home'         , description: 'Main tool'           , sites:['s1', 's2']       ],
                t2  :[title:'Announcements', description: 'System Announcements', sites:['s1', 's2', 's4'] ],
                t3  :[title:'Gradebook'    , description: 'Grades'              , sites:['s4']             ]
                t4  :[title:'Chat'         , description: 'Chat server'         , sites:['s4']             ]
                t5  :[title:'Resources'    , description: 'File Resources'      , sites:['s4']             ]
                t6  :[title:'Gradebook2'   , description: 'Student Grades'      , sites:['s4']             ]
                t7  :[title:'Forums'       , description: 'Site Forums'         , sites:['s4']             ]
]

def siteTools = [
        s1:[t1, t2, t3, t4],
        s2:[t1, t3, t4, t5],
        s3:[t1, t3, t6,t7],
        s4:[t1, t5, t7]
]

// find user in map via key and password value
def login(String username, String pword) {
    def result = null
    def user = users.get(username)
    if (user != null) {
        // if pwd
        result = user.sessId;
    }
    return result;
}

// get all sites valid by session id
def getSites(String sessId) {
    def siteList = []
    users.each{ k,v->
        if (v.sessId == sessId) {
            v.sites.each{ sitekey ->
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
        result << [name:site.name, id:site.siteId, description:site.description, type:site.stype]
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
    def site = getSite(siteId)
    if (site) {
        sites.each{ site->


        }


    }

}


}
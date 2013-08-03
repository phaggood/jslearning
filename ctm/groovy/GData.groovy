public class GData {


 def sites = [
        s001:[name: 'ACT-105', description: 'Intro Accounting', stype:'course', image:"debate"],
        s002:[name: 'ART-201', description: 'Pottery Lab', stype:'course', image:"sculpture"],
        s003:[name: 'AST-211', description: 'Astronomy', stype:'course', image:"space_science"],
        s004:[name: 'CS-101', description: 'Intro Comp Sci', stype:'course', image:"cs_intro"] ,
        s005:[name: 'CS-201', description: 'Game Development Lab', stype:'course', image:"game_dev"],
        s006:[name: 'HIS-105', description: 'American Revolution', stype:'course', image:"history101"],
        s007:[name: 'DAN-315', description: 'Ballroom Dance', stype:'course', image:"dance"],
        s008:[name: 'PHY-101', description: 'Intro to Physics', stype:'course', image:"physics"],
        ]


 def users = [
                bjones :[password:'pword', fname: 'Bob' , lname: 'Jones', sessId:'ss001', sites:['s001', 's002','s006']        ],
                jdoe   :[password:'pword', fname: 'Jane', lname: 'Doe',   sessId:'ss002', sites:['s001', 's002','s004','s008'] ],
                tdoc   :[password:'pword', fname: 'Doctor', lname: 'Who', sessId:'ss003', sites:['s001', 's004','s005','s006'] ],
                mbill  :[password:'pword', fname: 'Mr'  , lname: 'Bill',  sessId:'ss004', sites:['s002', 's003','s004','s007'] ]
            ]


 def tools = [
                t1  :[name:'sakai-home'      ,title:'Home'         , description: 'Main tool'           , image:'home.jpeg'          ],
                t2  :[name:'sakai-announce'  ,title:'Announcements', description: 'System Announcements', image:'announcements.jpeg' ],
                t3  :[name:'sakai-gradebook' ,title:'Gradebook'    , description: 'My Grades'           , image:"grades.png"         ],
                t4  :[name:'sakai-chat'      ,title:'Chat'         , description: 'Chat server'         , image:'chat.jpeg'          ],
                t5  :[name:'sakai-rsc'       ,title:'Resources'    , description: 'File Resources'      , image:'folder.jpeg'        ],
                t6  :[name:'kaltura'         ,title:'Media Lib'   , description:  'Media Service'       , image:'media.jpg'          ],
                t7  :[name:'sakai-forum'     ,title:'Forums'       , description: 'Site Forums'         , image:"forum.jpeg"         ],
                t8  :[name:'sakai-stats'     ,title:'My Stats'     , description: 'Student Overall Standing'  , image:"stats.jpeg" ] ,
                t8  :[name:'sakai-assignments'     ,title:'Assignments'     , description: 'Student Assignments'  , image:"assignments.jpeg" ]

]

 def siteTools = [
        s001:['t1', 't2', 't3', 't4'],
        s002:['t1', 't3', 't4', 't5'],
        s003:['t1', 't3', 't6', 't7'],
        s004:['t1', 't5', 't2', 't6','t7', 't8'] ,
        s005:['t1', 't5', 't4', 't5', 't7'] ,
        s006:['t1', 't2', 't3', 't5','t6', 't7'] ,
        s007:['t1', 't5', 't6', 't7'] ,
        s008:['t1', 't4', 't5', 't7','t8'] ,
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
    def usr = [:]
    users.each{ k,v->
        if (v.sessId == sessId) {
            //println "found user ${k}"
            usr = getUserByUserId(k)
        }
    }
    return usr
}

def getUserByUserId(String userid) {
    def result = [:]
    def user = users.get(userid) // userid is same as username here
    if (user) {
       result << [firstname:user.fname, lastname:user.lname, userid:userid, sites:user.sites]
    }
    return result
}


def getSitesBySession(String sessId){
    def result = []
    def usr = getUserBySessionId(sessId)
    if (usr) {
        def sitelist = usr.sites
        sitelist.each{ usrsiteId ->
            result << getSite(usrsiteId)
        }
    }
    return result
}

def getToolCount(String siteId) {
    def tools = siteTools.get(siteId)
    if(tools) return tools.size()
    else return 0

}

// get site by siteId
def getSite(String siteId) {
    def result = [:]
    def site = sites.get(siteId)
    if (site) {
        result << [name:site.name, id:"${siteId}", description:site.description, type:site.stype, thumbnail: site.image, toolCount: getToolCount(siteId)]
    }
    return result
}

def getTool(String toolId) {
    def result = [:]
    def tool = tools.get(toolId)
    if (tool) {
        result << [id:toolId, name:tool.name, title:tool.title, description:tool.description, thumbnail:tool.image]
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
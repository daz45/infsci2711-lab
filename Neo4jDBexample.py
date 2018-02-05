from py2neo import Graph, Node, Relationship

def cleanDB():
    graph.delete_all()

def showAll():
    results = graph.find(label="Person")
    for f in results:
        print(f)
    print("========================")

def addNodeRel():
    peter=Node("Person", "student", name="Peter", age=25, gender="male")
    kelly=Node("Person", "player", name="Kelly", age=20, gender="female")
    kary=Node("Person", "student", name="Kary", age=25, gender="female")
    alia=Node("Person", "officer", name="alia", age=23, gender="female")
    graph.create(peter)
    graph.create(kelly)
    graph.create(kary)
    graph.create(alia)

    aFriendRel = Relationship(kary, 'friends_of', peter)
    aFriendRel['years'] = 2
    graph.create(aFriendRel)

def update():
    kary=Node("Person", "student", name="Kary", age=25, gender="female")
    peter=Node("Person", "student", name="Peter", age=25, gender="male")
    aFriendRel = Relationship(kary, 'friends_of', peter)
    aFriendRel['years'] =2
    graph.push(aFriendRel)

def search():
    result = graph.find_one(
        label="Person",
        property_key="gender",
        property_value="female"
    )
    print(result)
    print(result['name'])


graph = Graph("localhost:7474", username = "neo4j", password = "112358")
cleanDB()
addNodeRel()
showAll()
update()
showAll()
search()






namespace java com.choy.thriftplus.eureka.test.gen

service ObjectIdGenerator {
    string getObjectId(string id)
}

service ExternalService {
    string externalService(string token)
}
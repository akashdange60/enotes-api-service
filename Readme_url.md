Normal API used within postman

1)POST.....http://localhost:8080/api/v1/category/save
Body..........raw.............JSON
{
     "id":7,
    "name":"GoLang     ",
    "description":"This is GoLang Category",
    "isActive":false

}          
2)GET......http://localhost:8080/api/v1/category/

3)GET......http://localhost:8080/api/v1/category/7

	Auditing

Spring Data provides sophisticated support to transparently keep track of who created or changed an entity and when the change happened.To benefit from that functionality, you have to equip your entity classes with auditing metadata that can be defined either using annotations or by implementing an interface. Additionally, auditing has to be enabled either through Annotation configuration or XML configuration to register the required infrastructure components. Please refer to the store-specific section for configuration samples.
 
	Annotation-based Auditing Metadata
	
We provide @CreatedBy and @LastModifiedBy to capture the user who created or modified the entity as well as @CreatedDate and @LastModifiedDate to capture when the change happened.


POST mapping .......... http://localhost:8080/api/v1/notes/

BODY.......... 
{
      "title":"Java Programming",
    "description":"Java is Progaramming language",
    "category":{
        "id":10
    }
  
}

GET mapping .......... http://localhost:8080/api/v1/notes/

	When file is going to be upload at that time the end points are below
POST mapping   .......... http://localhost:8080/api/v1/notes/

key..........  notes 
values..........{
      "title":"DSA Programming",
    "description":"DSA is Progaramming language",
    "category":{
        "id":2
    }
    
}
key........file
values............ java.pdf or empty          


GET mapping .......... http://localhost:8080/api/v1/notes/

	For Downloading file
GET Mapping .......http://localhost:8080/api/v1/notes/download/2

	For pagination use below api
GET Mapping..........http://localhost:8080/api/v1/notes/user-notes	
Param
key ...............values
pageNo..............2
pageSize............3

	For new Entry
POST Mapping ................http://localhost:8080/api/v1/notes/	

formdata
key................value
notes............{
					"id": 28,
      				"title":" AWS/GCP/Azure cloud",
    				"description":"AWS/GCP/Azure cloud is for Deployment",
   					 "category":{
       					 "id":10
    							}
					}
file................ ADD Any file here
	
	For update
POST Mapping ................http://localhost:8080/api/v1/notes/	

formdata
key................value
notes............{
      				"title":" AWS/GCP/Azure 1 cloud",
    				"description":"AWS/GCP/Azure 1 cloud is for Deployment",
   					 "category":{
       					 "id":10
    							}
					}
file................ ADD Any file here



             
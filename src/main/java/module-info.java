module book.my.show
{
    /* about anotations used in this projects :

    1. @Controller -> it is the place where the http requests are made or we can say all the endpoints of API's are written here if a developer wants to see
    all the end points he/she can directly come to the controller package where all the controllers for different entities are written

    2. @Service -> service annotation internally has @Component annotation by which the object/bean is managed by the ioc container and it can be used by
    using @Autowired annotation which is used for dependency injection

    3. @Repository -> it also internally has @Component annotation. it is the layer which has connection to db where all the crud operations of the db takes
    place. JpaRepository is used for making all the crud related operations which has Mysql and by it also uses hibernate to simplify operations by writing simple
    functions ex:- .save , .saveAll, .findById, etc..,  (JPA) -> JAVA PERSISTENCE API.  It is an object-relational mapping (ORM) framework that allows us to map
    Java objects to tables in a relational database.

    4. @Autowired -> it is used for dependency injection which means we can use the object/bean of a class without initializing it everytime we can initialize
    the object with the help of constructor, setter injection methods under @Configuration class as per need

    5. @ResponseEntity -> it is used for returning the http response(status) with the different responses there are multiple type of responses like put, post, get, delete etc..,

    6. @RestController -> it is combination of @Response and @Controller

    7. @Entity  -> marks a class as table in the db

    8. @Table -> declared on top of the models to create a table

    9. @AllArgsConstructor -> used for creating all args constructor

    10. @NoArgsConstructor -> used for creating a no args constructor

    11. @Getter & @Setter -> it is used to declare the getter setters of a class without creating them we can directly use it.

    12. @Id -> for declaring as a column as a primary key in table

    13. @OneToMany @ManyToOne @OneToOne etc.,

    14. @Data -> internally has @Getter @Setter @RequiredArgsConstructor

    15. @GetMapping -> used for get apis

    16. @PostMapping -> used for post apis

    17. @RequestMapping -> it declares a prefix on a api call on top of the controller for a specific usage

    18. @RequestParam -> its is used to get the variables required in the apis path directly

    19. @RequestBody -> it is used to get the json body

    20. @PathVariable -> sane as request param but somewhat different in implementation

    21. @Builder -> it is another way to initialize the object in spring
 */
}
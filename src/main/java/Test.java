import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.ArrayList;


public class Test {
  
  public static void main(String[] args) {
    Test t = new Test();
    // getting the schema
    GraphQLSchema schema = t.schema();
    GraphQL graphQL = GraphQL.newGraphQL(schema).build();
    
    String query = "mutation {Create(student_name:\"Student\"){student_name}}";
    //String query="{__schema{mutationType{name fields{name type{name kind}}}}}";
    //String  query="{__schema{types{name}}}";
    
    ExecutionInput executionInput = ExecutionInput.newExecutionInput().query(query).build();
    ExecutionResult executionResult = graphQL.execute(executionInput);
    System.out.println(executionResult);
    Object data = executionResult.getData();
    System.out.println(data);
    System.out.println(executionResult.getErrors());
  }
  
  GraphQLSchema schema() {
    ArrayList<String> values = new ArrayList<>();
    values.add("student_name");
    values.add("student_description");
    values.add("student_status");
    
    GraphQLObjectType.Builder objectType1;
    MutationFetcher mutationFetcher = new MutationFetcher();
    
    // defining the student
    objectType1 = GraphQLObjectType.newObject().name("Student").description("Main Entity");
    for (int j = 0; j < 3; j++) {
      objectType1.field(GraphQLFieldDefinition.newFieldDefinition()
        .name(values.get(j))
        .type(Scalars.GraphQLString));
    }
    objectType1.build();
    GraphQLObjectType req = objectType1.build();
    
    // defining the root element query
    GraphQLObjectType.Builder objectType = GraphQLObjectType.newObject().name("Query");
      objectType.field(GraphQLFieldDefinition.newFieldDefinition()
        .name("Create")
        .argument(GraphQLArgument.newArgument()
          .name("student_name")
          .type(Scalars.GraphQLString))
        .type(req)
        .dataFetcher(mutationFetcher));
      objectType.build();
    
    
    //defining the  mutation
    GraphQLObjectType.Builder check = GraphQLObjectType.newObject()
      .name("check")
      .field(GraphQLFieldDefinition.newFieldDefinition()
        .name("Create")
        .type(req)
        .argument(GraphQLArgument.newArgument()
          .name("student_name")
          .type(Scalars.GraphQLString))
        .dataFetcher(mutationFetcher)
        .build());
    
    GraphQLSchema schema = GraphQLSchema.newSchema().query(objectType.build()).mutation(check).build();
    schema.getMutationType();
    return schema;
  }
  
}

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class MutationFetcher implements DataFetcher {
  
  @Override
  public Object get(DataFetchingEnvironment environment) {
    try {
      
      String name = environment.getArgument("student_name");
      System.out.println("hi I am in mutation fetcher");
    } catch (Exception ex) {
      ex.printStackTrace();
      
    }
    return null;
  }
  
}

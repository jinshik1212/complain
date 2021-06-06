package airbnb;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="complains", path="complains")
public interface ComplainRepository extends PagingAndSortingRepository<Complain, Long>{


}

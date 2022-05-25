@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface $Entity$Mapper {

  @Mappings(
      @Mapping(target = "id", ignore = true)
  )
  $Entity$ fromDto($Entity$CreateDto dto);

  $Entity$Dto toDto($Entity$ $entity$);

}

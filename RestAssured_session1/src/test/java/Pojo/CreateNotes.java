package Pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public  class CreateNotes {
    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;
}

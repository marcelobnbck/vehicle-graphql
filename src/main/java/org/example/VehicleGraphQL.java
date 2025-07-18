import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;

import graphql.schema.GraphQLString;

import java.util.Arrays;
import java.util.List;

public class VehicleGraphQL {

    // This represents the data our GraphQL API will expose.
    static class Vehicle {
        String id;
        String name;
        int crew;

        public Vehicle(String id, String name, int crew) {
            this.id = id;
            this.name = name;
            this.crew = crew;
        }

        // Getters to read the object's properties
        public String getId() { return id; }
        public String getName() { return name; }
        public int getCrew() { return crew; }
    }

    // In-memory Data Store
    private static final List<Vehicle> vehicles = Arrays.asList(
            new Vehicle("car-1", "Chevette", 5),
            new Vehicle("car-2", "Fusca", 4),
            new Vehicle("car-3", "Landau", 10)
    );

    public static void main(String[] args) {

        // Define the 'Vehicle' type:
        GraphQLObjectType vehicleType = GraphQLObjectType.newObject()
                .name("Vehicle") // The name of the GraphQL type
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("id")
                        .type(GraphQLString.nonNull())
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("name")
                        .type(GraphQLString.nonNull())
                        .build())
                .field(GraphQLFieldDefinition.newFieldDefinition()
                        .name("crew")
                        .type(GraphQLInt)
                        .build())
                .build();

        // DataFetcher for the 'vehicleById' query
        DataFetcher<Vehicle> vehicleByIdDataFetcher = environment -> {
            String vehicleId = environment.getArgument("id"); // Get the 'id' argument from the query
            return vehicles.stream()
                    .filter(vehicle -> vehicle.getId().equals(vehicle))
                    .findFirst()
                    .orElse(null); // Return null if not found
        };

        System.out.println("\nQuery Result:");
        System.out.println(executionResult.toSpecification());

        // Check for errors
        if (!executionResult.getErrors().isEmpty()) {
            System.out.println("\nQuery Errors:");
            executionResult.getErrors().forEach(System.out::println);
        }

    }
}
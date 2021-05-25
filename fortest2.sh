curl -X GET http://localhost:8080/users/recommendations -H "Content-type:application/json" -d '{"gender": "F", "age": "25", "occupation": "Grad student", "genre": "Action"}'
curl -X GET http://localhost:8080/movies/recommendations -H 'Content-type:application/json' -d '{"title": "Toy Story (1995)", "limit": 20}'

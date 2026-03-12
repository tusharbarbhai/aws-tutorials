# Spring Boot DynamoDB API (with Style Reviewer)

This project exposes:

- CRUD APIs for `Person`
- a style reviewer API: `POST /api/style-review`

## Prerequisites

- Java 11+
- Maven 3.8+
- AWS credentials with DynamoDB access **or** a local DynamoDB endpoint

> Note: the repository currently does not include `.mvn/wrapper/*`, so use `mvn` directly instead of `./mvnw`.

## 1) Configure DynamoDB connection

Edit `src/main/resources/application.properties`:

```properties
server.port=5000

dynamodb.region=us-east-1
dynamodb.serviceEndpoint=dynamodb.us-east-1.amazonaws.com
dynamodb.accessKey=YOUR_ACCESS_KEY
dynamodb.secretKey=YOUR_SECRET_KEY
```

If you want local DynamoDB, set endpoint to your local URL (for example `http://localhost:8000`) and matching test credentials.

## 2) Create the `Person` table

The app expects a DynamoDB table named `Person` with:

- Partition key: `personId` (String)

Example (AWS CLI):

```bash
aws dynamodb create-table \
  --table-name Person \
  --attribute-definitions AttributeName=personId,AttributeType=S \
  --key-schema AttributeName=personId,KeyType=HASH \
  --billing-mode PAY_PER_REQUEST \
  --region us-east-1
```

## 3) Run the app

From project root:

```bash
cd spring-boot-dynamodb
mvn spring-boot:run
```

The API starts on `http://localhost:5000`.

## 4) Test APIs

### Style reviewer API

```bash
curl -X POST "http://localhost:5000/api/style-review" \
  -H "Content-Type: application/json" \
  -d '{
    "userPhotoUrl":"https://example.com/user.jpg",
    "clothingItemId":"ITEM-123",
    "clothingName":"Slim Fit Navy Blazer",
    "marketplace":"StyleMart",
    "skinTone":"warm",
    "bodyShape":"athletic",
    "age":28,
    "dressingStyle":"smart casual"
  }'
```

Expected response contains:

- `score` (0-100)
- `verdict`
- `generatedPreviewImageUrl`
- `explanation`
- `strengths`
- `suggestions`

### Person APIs

```bash
# Add
curl -X POST "http://localhost:5000/addPerson" -H "Content-Type: application/json" -d '{"firstName":"Ada","lastName":"Lovelace","age":36,"email":"ada@example.com"}'

# Find
curl "http://localhost:5000/findPerson/{personId}"

# Update
curl -X PUT "http://localhost:5000/updatePerson" -H "Content-Type: application/json" -d '{"personId":"{personId}","firstName":"Ada","lastName":"Lovelace","age":37,"email":"ada@example.com"}'

# Delete
curl -X DELETE "http://localhost:5000/deletePerson/{personId}"
```

## Troubleshooting

- **403 downloading Maven deps**: check environment/firewall/proxy rules.
- **Auth/connection errors to DynamoDB**: verify `dynamodb.*` values in `application.properties`.
- **Table not found**: ensure table name is exactly `Person`.

# Heatmap Data API

## About
This application exposes an endpoint providing answer rate info based on call logs of an organization, formatted for potential use in a heatmap visualization.

## Requirements to run
[JDK 17](https://jdk.java.net/17/)

## Running instructions
Copy the project locally and run `./gradlew bootRun` in the command line to start the application on `localhost:8080`.
Use `./gradlew test` to run tests.

The application has an in-built database that contains 900 entries from the period of 04-04-2025 to 07-04-2027.

The application is secured via HTTP Basic Authentication. Use the pre-configured user `test_user / superMegaSecurePassword`

## Endpoints
### Answer Rate Heatmap Endpoint `/api/heatmap/answer-rate`
Returns hourly call statistics for a given date and time range, formatted for use in a heatmap.

**Request Parameters**

| Parameter        | Type      | Required | Description                                                                 |
|------------------|-----------|----------|-----------------------------------------------------------------------------|
| `dateInput`      | `String`  | yes      | Target date in `YYYY-MM-DD` format.                                        |
| `numberOfShades` | `Integer` | yes      | Number of heatmap shades (minimum **3**, maximum **10**).                  |
| `startHour`      | `Integer` | no       | Start hour for the range (0–23). Defaults to `0` if omitted.               |
| `endHour`        | `Integer` | no       | End hour for the range (0–23, must be ≥ `startHour`). Defaults to `23`.    |

**Response**

Returns a JSON array of objects, one for each hour in the range `[startHour, endHour]`.

```json
[
  {
    "hour": 14,
    "answeredCalls": 8,
    "totalCalls": 10,
    "rate": 80.0,
    "shade": "Shade4"
  }
]
```

**Shade Mapping Example for numberOfShades = 5**

| Rate (%)         | Shade   |
|------------------|---------|
| 0.0% – 20.0%     | Shade1  |
| > 20.0% – 40.0%  | Shade2  |
| > 40.0% – 60.0%  | Shade3  |
| > 60.0% – 80.0%  | Shade4  |
| > 80.0% – 100.0% | Shade5  |


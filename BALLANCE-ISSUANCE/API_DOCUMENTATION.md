# Balance Issuance (BI) Module - API Documentation

## Endpoint: POST /bi/generate

### Business Logic Summary
The BI module generates monthly payments for approved eligibility plans and creates Excel reports. This endpoint allows **manual triggering** of the automated monthly payment generation process.

### Request Type: **POST** ✅
- **Reason**: Creates data in database, generates files (side effects), and executes a command
- **Request Body**: Optional (all fields have intelligent defaults)
- **Content-Type**: `application/json`

---

## Request Body Schema

### Field Descriptions

| Field | Type | Default | Description |
|-------|------|---------|-------------|
| `planStatus` | String | "APPROVED" | Filter eligibility records by plan status |
| `paymentDate` | String (ISO-8601 Date) | Today's date | Date to assign to generated payments |
| `paymentStatus` | String | "SUCCESS" | Status to mark on payment records |
| `excelFilePath` | String | "C:/HIS/Sharepoint/BI_Payments" | Base path for Excel output file |

---

## Request Examples

### Example 1: Minimal Request (Use All Defaults)
```json
{}
```
**Result**: Generates payments for all APPROVED plans with today's date and SUCCESS status

---

### Example 2: Custom Payment Date
```json
{
  "paymentDate": "2026-05-01"
}
```
**Result**: Generates payments dated May 1, 2026 for all APPROVED plans

---

### Example 3: Filter by Different Plan Status
```json
{
  "planStatus": "PENDING",
  "paymentDate": "2026-04-28",
  "paymentStatus": "PENDING"
}
```
**Result**: Generates PENDING payments for all PENDING eligibility records

---

### Example 4: Custom Excel Export Path
```json
{
  "planStatus": "APPROVED",
  "paymentDate": "2026-04-28",
  "paymentStatus": "SUCCESS",
  "excelFilePath": "D:/Reports/Monthly_Payments"
}
```
**Result**: Exports to `D:/Reports/Monthly_Payments_2026-04-28.xlsx`

---

### Example 5: Full Configuration
```json
{
  "planStatus": "APPROVED",
  "paymentDate": "2026-06-01",
  "paymentStatus": "SUCCESS",
  "excelFilePath": "C:/HIS/Sharepoint/BI_June_Payments"
}
```

---

## Response

### Success Response
```json
{
  "status": 200,
  "message": "Monthly payments generated and Excel file created."
}
```

---

## Business Logic Flow

1. **Parse Request** → Use provided values or defaults
2. **Query Eligibility** → Fetch all records matching `planStatus`
3. **Create Payments** → Insert payment records with specified `paymentDate` and `paymentStatus`
4. **Generate Excel** → Create Excel file at `excelFilePath` with timestamp
5. **Return Response** → Confirm successful execution

---

## Notes

- ✅ Request body is **completely optional** - can send `{}` or empty body
- ✅ All fields are **autonomous** - each can be omitted independently  
- ✅ Excel file includes timestamp: `{excelFilePath}_YYYY-MM-DD.xlsx`
- ✅ The endpoint is **safe** - can be called multiple times without causing duplicates on the same date
- ⚠️ Excel generation uses today's date in filename, not the `paymentDate` parameter

---

## Historical Context

The BI module was originally designed to run automatically via scheduler:
- **Scheduled Execution**: 2 AM on 1st of every month
- **Manual Trigger**: Now available via this POST endpoint for ad-hoc processing

This endpoint allows administrators to:
- Generate payments for a specific date (e.g., retroactive processing)
- Process different plan statuses
- Save reports to custom locations
- Override payment status (for testing/corrections)

---

## Testing in Postman

**URL**: `http://localhost:8089/bi/generate`

**Method**: `POST`

**Headers**:
```
Content-Type: application/json
Authorization: Bearer <token> (if required)
```

**Body**:
```json
{
  "planStatus": "APPROVED",
  "paymentDate": "2026-04-28",
  "paymentStatus": "SUCCESS",
  "excelFilePath": "C:/HIS/Sharepoint/BI_Payments"
}
```

**Or empty for defaults**:
```json
{}
```


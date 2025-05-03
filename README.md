# platkmframework-dsl

Domain-Specific Language (DSL).

## Registro Cliente Bancario

```plaintext
         ValidateCustomer
               ↓
         VerifyIdentity
               ↓
        ┌──── isVerified ────┐
        ↓                   ↓
    SaveCustomer       NotifyAnalyst
        +                      
Parallel:                          
 - SendWelcomeEmail             
 - IssueCard                    
        ↓
    (fin)
```

## Checkout Ecommerce

```plaintext
           ValidateCart
                 ↓
            CheckStock
                 ↓
        ┌── isStockAvailable ──┐
        ↓                     ↓
  Parallel:               NotifyOutOfStock
   - ReserveInventory      +     
   - ProcessPayment     CancelCart
        ↓
     GenerateOrder
        ↓
       (fin)
```

## Reporte Financiero

```plaintext
      ValidateReportRequest
                ↓
        Parallel:
         - FetchSales
         - FetchExpenses
         - FetchTaxes
                ↓
        MergeReportData
                ↓
         GeneratePDF
                ↓
        SendReportEmail
```

## Autenticacion Mfa

```plaintext
     ValidateCredentials
               ↓
       ┌─ requiresMfa ─┐
       ↓              ↓
SendMfaCode      SkipMfaStep
      ↓               ↓
ValidateMfaCode      (salto)
       ↓              ↓
    LoginSuccess ←────┘
```

## Gestion Documental

```plaintext
           ValidateFormat
                 ↓
          OCR Extraction
                 ↓
        Validate Metadata
                 ↓
        ┌────── isValid ──────┐
        ↓                    ↓
   SaveToDB              NotifyOperator
     +                    +     ↓
Parallel:          MoveToReviewFolder
 - Index           ←────────────┘
 - GeneratePreview
        ↓
FinalizeProcessing
```




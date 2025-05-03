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
        
        List<FlowStep<Report>> list  = List.of(
                new  FetchSales(),
                new FetchExpenses(),
                new FetchTaxes(),
                new MergeReportData(),
                new GeneratePDF(),
                new SendReportEmail());

        FlowDefinitionContext<Report> context = new FlowDefinitionContext<>(list);
        FlowDefinitionRunner<Report> fdRunner = FlowDefinitionBuilder.
                builder(context).
                parallel(FetchSales.class, FetchExpenses.class, FetchTaxes.class).
                step(MergeReportData.class).
                step(GeneratePDF.class).
                step(SendReportEmail.class).
                build();

        Report report = new Report();
        fdRunner.run(report);
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




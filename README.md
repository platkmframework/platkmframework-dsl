# platkmframework-dsl

Domain-Specific Language (DSL).

## Bank Customer Registration

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
    
        List<FlowStep<Client>> list  = List.of(
                new ValidateCustomer(),
                new VerifyIdentity(),
                new SaveCustomer(),
                new NotifyAnalyst(),
                new SendWelcomeEmail(),
                new IssueCard());

        FlowDefinitionContext<Client> context = new FlowDefinitionContext<>(list);
        fdRunner = FlowDefinitionBuilder.
                builder(context).
                    step(ValidateCustomer.class).
                    log("Validate Customer").
                    step(VerifyIdentity.class).
                    log("Verify Identity").
                    when(c -> c.isVerified).
                        step(SaveCustomer.class).log("Save Customer").
                        parallel(SendWelcomeEmail.class, IssueCard.class).
                    whenElse(c -> !c.isVerified).
                        step(NotifyAnalyst.class).log("Notify Analyst").
                    end().
                build();

        Client client = new Client();
        fdRunner.run(client);
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
       
        List<FlowStep<Payment>> list = List.of(new ValidateCart(),
                new CheckStock(),
                new ReserveInventory(),
                new ProcessPayment(),
                new NotifyOutOfStock(),
                new CancelCart(),
                new GenerateOrder());

        FlowDefinitionContext<Payment> context = new FlowDefinitionContext<>(list);

        fdRunner = FlowDefinitionBuilder.
                builder(context).
                    step(ValidateCart.class).
                    step(CheckStock.class).
                    when(p-> p.isStockAvailable).
                        parallel(ReserveInventory.class, ProcessPayment.class).
                        step(GenerateOrder.class).
                    whenElse(p-> !p.isStockAvailable).
                        step(NotifyOutOfStock.class).
                        step(CancelCart.class).
                    end().
                build();
```

## Generation of Financial Reports

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
         fdRunner = FlowDefinitionBuilder.
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
    
        List<FlowStep<Credential>> list  = List.of(
                new  SendMfaCode(),
                new ValidateMfaCode(),
                new SkipMfaStep(),
                new LoginSuccess());

        FlowDefinitionContext<Credential> context = new FlowDefinitionContext<>(list);
        fdRunner = FlowDefinitionBuilder.
                builder(context).
                    when(credential -> credential.requiredMFA).
                        step(SendMfaCode.class).
                        step(ValidateMfaCode.class).
                        step(LoginSuccess.class).
                    whenElse(credential -> !credential.requiredMFA).
                        step(SkipMfaStep.class).
                        step(LoginSuccess.class).
                    end().
                build();

        Credential credential = new Credential();
        credential.requiredMFA = true;
        fdRunner.run(credential);
```

## Document Management

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

        List<FlowStep<Document>> list  = List.of(
                new ValidateFormat(),
                new ORCExtraction(),
                new ValidateMetaData(),
                new SaveToDB(),
                new NotifyOperator(),
                new MoveToReviewFolder(),
                new Index(),
                new GeneratePreview(),
                new FinalizeProcessing()
        );

        FlowDefinitionContext<Document> context = new FlowDefinitionContext<>(list);
        fdRunner = FlowDefinitionBuilder.
                builder(context).
                    step(ValidateFormat.class).
                    step(ORCExtraction.class).
                    step(ValidateMetaData.class).
                    when(document -> document.isValid).
                        step(SaveToDB.class).
                        parallel(Index.class, GeneratePreview.class).
                    whenElse(document -> !document.isValid).
                        step(NotifyOperator.class).
                        step(MoveToReviewFolder.class).
                        parallel(Index.class, GeneratePreview.class).
                    end().
                    step(FinalizeProcessing.class).
                build();

        Document document = new Document();
        document.isValid = Boolean.TRUE;
        fdRunner.run(document);
```

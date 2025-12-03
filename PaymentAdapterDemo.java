public class PaymentAdapterDemo {

    // Método exigido para teste, recebendo a nova interface PPay
    public static void testPPay(PPay pp){
        System.out.println("\n--- Dados do Objeto PPay (PayPal) ---");
        System.out.println("Nome do Proprietário: " + pp.getCardOwnerName()); 
        System.out.println("Número do Cartão: " + pp.getCustCardNo());
        System.out.println("Data de Expiração (MM/AA): " + pp.getCardExpMonthDate()); 
        System.out.println("CVV: " + pp.getCVVNo()); 
        System.out.println("Valor Total: " + pp.getTotalAmount()); 
        System.out.println("-------------------------------------");
    }

    // Método que simula uma das 100 classes que esperam um objeto MPay
    public static void processPayment(MPay paymentSystem) {
        System.out.println("\n--- Processando Pagamento no Sistema Legado (espera MPay) ---");
        System.out.println("Sistema de Pagamento Usado: " + paymentSystem.getClass().getSimpleName());

        // O sistema legado continua usando a interface MPay.
        paymentSystem.setCustomerName("Maria Oliveira");
        paymentSystem.setCreditCardNo("9999888877776666");
        paymentSystem.setCardExpMonth("12"); // Chamada MPay (mês separado)
        paymentSystem.setCardExpYear("30");   // Chamada MPay (ano separado)
        paymentSystem.setCardCVVNo((short) 123); // Tipo Short
        paymentSystem.setAmount(250.50);

        System.out.println("Montante a Pagar (via MPay interface): R$ " + paymentSystem.getAmount());

        // Chamada de pagamento
        if (paymentSystem instanceof PPayAdapter) {
            ((PPayAdapter) paymentSystem).processPayment();
        }
    }

    public static void main(String[] args) {

        // 1. Cria a implementação do novo sistema (Adaptee)
        PPay novoPayPal = new PPayImpl();

        // 2. Cria o Adaptador (Target), injetando o novo sistema
        MPay adaptadorParaPPay = new PPayAdapter(novoPayPal);

        // 3. O código legado usa o Adaptador, sem saber que está usando o novo serviço!
        processPayment(adaptadorParaPPay);

        // 4. Chamada do método de teste exigido, usando o objeto PPay que foi populado pelo Adapter.
        testPPay(novoPayPal);
    }
}
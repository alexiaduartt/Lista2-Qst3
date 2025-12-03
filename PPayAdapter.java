public class PPayAdapter implements MPay {
    private PPay ppay;
    
    // Variáveis temporárias para lidar com setCardExpMonth/Year
    private String tempMonth = null;
    private String tempYear = null;

    public PPayAdapter(PPay ppay) {
        this.ppay = ppay;
    }

    // --- Mapeamento e Adaptação dos Métodos (GETTERS) ---
    @Override
    public String getCreditCardNo() {
        return ppay.getCustCardNo();
    }

    @Override
    public String getCustomerName() {
        return ppay.getCardOwnerName();
    }

    // Adaptação: Pega o mês da string completa "MM/AA"
    @Override
    public String getCardExpMonth() {
        String fullDate = ppay.getCardExpMonthDate();
        if (fullDate != null && fullDate.contains("/")) {
            return fullDate.substring(0, fullDate.indexOf("/"));
        }
        return fullDate;
    }

    // Adaptação: Pega o ano da string completa "MM/AA"
    @Override
    public String getCardExpYear() {
        String fullDate = ppay.getCardExpMonthDate();
        if (fullDate != null && fullDate.contains("/")) {
            return fullDate.substring(fullDate.indexOf("/") + 1);
        }
        return null;
    }

    // Adaptação e Conversão de Tipo: MPay usa Short, PPay usa Integer
    @Override
    public Short getCardCVVNo() {
        Integer cvv = ppay.getCVVNo();
        return (cvv != null) ? cvv.shortValue() : null;
    }

    @Override
    public Double getAmount() {
        return ppay.getTotalAmount();
    }

    // --- Mapeamento e Adaptação dos Métodos (SETTERS) ---
    @Override
    public void setCreditCardNo(String creditCardNo) {
        ppay.setCustCardNo(creditCardNo);
    }

    @Override
    public void setCustomerName(String customerName) {
        ppay.setCardOwnerName(customerName);
    }

    // Adaptação: Armazena o mês e tenta atualizar a data completa em PPay
    @Override
    public void setCardExpMonth(String cardExpMonth) {
        this.tempMonth = cardExpMonth;
        if (this.tempYear != null) {
            ppay.setCardExpMonthDate(this.tempMonth + "/" + this.tempYear);
        }
    }

    // Adaptação: Armazena o ano e tenta atualizar a data completa em PPay
    @Override
    public void setCardExpYear(String cardExpYear) {
        this.tempYear = cardExpYear;
        if (this.tempMonth != null) {
            ppay.setCardExpMonthDate(this.tempMonth + "/" + this.tempYear);
        }
    }

    // Adaptação e Conversão de Tipo: MPay usa Short, PPay usa Integer
    @Override
    public void setCardCVVNo(Short cardCVVNo) {
        ppay.setCVVNo((cardCVVNo != null) ? cardCVVNo.intValue() : null);
    }

    @Override
    public void setAmount(Double amount) {
        ppay.setTotalAmount(amount);
    }
    
    public boolean processPayment() {
        System.out.println("ℹ️ Adaptando chamada de pagamento MPay para PPay...");
        if (ppay instanceof PPayImpl) {
             return ((PPayImpl) ppay).executePayment();
        }
        return false;
    }
}
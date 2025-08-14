public class Budget {
    private Long totalBudget;
   private Long currentAmount;
   private User user;

    public Budget(long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void setTotalBudget(long totalBudget) {
        this.totalBudget = totalBudget;
        currentAmount=totalBudget;
    }

    public Budget() {
        totalBudget= 0L;
    }

    public void addAmount(long amount)
    {
        currentAmount+=amount;
        totalBudget+=amount;
    }

    public void subAmount(long amount)
    {
        currentAmount-=amount;
    }

    public long getTotalBudget() {
        return totalBudget;
    }

    public long getCurrentAmount() {
        return currentAmount;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setTotalBudgetDB(long totalBudget)
    {
        this.totalBudget=totalBudget;
    }

    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }
}


public class myTester {
	public static void main(String[] args) throws Exception {
		MyBigInteger test1 = new MyBigInteger("5000000000000000000000000000000000000000000000000000", 10);
		//MyBigInteger test2 = new MyBigInteger("2131", 3);
		//String result = (test1.convert(10)).toString(); 
		String result = test1.primeFactors().toString();
		System.out.println(result);
		
	}
}

public class Main {

    public static void main(String[] args) {
	    if(args.length == 0){
	        System.out.println("Usage: java Main filename.txt");
        }
        else{
            Parser parser = new Parser(args[0]);
        }
    }
}

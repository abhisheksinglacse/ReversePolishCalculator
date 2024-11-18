import java.util.*;
class ReversePolishCalculator {
    
   
    static int Prec(char ch)
    {
        switch (ch) {
            case '+':
            case '-':
            return 1;
            
            case '*':
            case '/':
            return 2;
            
            case '^':
            return 3;
        }
        return -1;
    }
    
    
    static boolean isOperand(char x)
    {
        return (x >= 'a' && x <= 'z') ||
        (x >= 'A' && x <= 'Z');
    }
    
    
    static boolean isalpha(char c)
    {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
        {
            return true;
        }
        return false;
    }
    
    
    static boolean isdigit(char c)
    {
        if (c >= '0' && c <= '9')
        {
            return true;
        }
        return false;
    }
    
    
    static int getPriority(char C)
    {
        if (C == '-' || C == '+')
        return 1;
        else if (C == '*' || C == '/')
        return 2;
        else if (C == '^')
        return 3;
        
        return 0;
    }
    
    
    static    boolean isOperator(char x)
    {
        switch(x)
        {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
            case '%':
            return true;
        }
        return false;
    }
    
    
    static String reverse(char str[], int start, int end)
    {
        char temp;
        while (start < end)
        {
            
            temp = str[start];
            str[start] = str[end];
            str[end] = temp;
            start++;
            end--;
        }
        return String.valueOf(str);
    }
    
    
    static String postfixToInfix(String exp)
    {
        Stack<String> s = new Stack<String>();
        
        for (int i = 0; i < exp.length(); i++)
        {
            
            if (isOperand(exp.charAt(i)))
            {
                s.push(exp.charAt(i) + "");
            }
            
            else
            {
                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();
                s.push("(" + op2 + exp.charAt(i) +
                op1 + ")");
            }
        }
        return s.peek();
    }
    
    
    public static String prefixToInfix(String exp)
    {
        Stack<String> stack = new Stack<>();
        
        int l = exp.length();
        
        for(int i = l - 1; i >= 0; i--)
        {
            char c = exp.charAt(i);
            if (isOperator(c))
            {
                String op1 = stack.pop();
                String op2 = stack.pop();
                
                String temp = "(" + op1 + c + op2 + ")";
                stack.push(temp);
            }
            else
            {
                stack.push(c + "");
            }
        }
        return stack.pop();
    }
    
    
    static String infixToPrefix(String exp)
    {
        
        Stack<Character> operators = new Stack<Character>();
        
        Stack<String> operands = new Stack<String>();
        
        for (int i = 0; i < exp.length(); i++)
        {
            
            if (exp.charAt(i) == '(')
            {
                operators.push(exp.charAt(i));
            }
            
            else if (exp.charAt(i) == ')')
            {
                while (!operators.empty() &&
                operators.peek() != '(')
                {
                    
                    String op1 = operands.peek();
                    operands.pop();
                    
                    String op2 = operands.peek();
                    operands.pop();
                    
                    char op = operators.peek();
                    operators.pop();
                    
                    String tmp = op + op2 + op1;
                    operands.push(tmp);
                }
                
                operators.pop();
            }
            
            else if (!isOperator(exp.charAt(i)))
            {
                operands.push(exp.charAt(i) + "");
            }
            
            else
            {
                while (!operators.empty() &&
                getPriority(exp.charAt(i)) <=
                getPriority(operators.peek()))
                {
                    
                    String op1 = operands.peek();
                    operands.pop();
                    
                    String op2 = operands.peek();
                    operands.pop();
                    
                    char op = operators.peek();
                    operators.pop();
                    
                    String tmp = op + op2 + op1;
                    operands.push(tmp);
                }
                
                operators.push(exp.charAt(i));
            }
        }
        
        while (!operators.empty())
        {
            String op1 = operands.peek();
            operands.pop();
            
            String op2 = operands.peek();
            operands.pop();
            
            char op = operators.peek();
            operators.pop();
            
            String tmp = op + op2 + op1;
            operands.push(tmp);
        }
        
        return operands.peek();
    }
    
    
    static String infixToPostfix(String exp)
    {
        String result = new String("");
        
        Stack<Character> stack = new Stack<Character>();
        
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);
            
            if (Character.isLetterOrDigit(c))
            result += c;
            
            else if (c == '(')
            stack.push(c);
            
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.peek();
                    stack.pop();
                }
                
                stack.pop();
            }
            else
            {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())) {
                    
                    result += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            }
        }
        
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
            return "Invalid Expression";
            result += stack.peek();
            stack.pop();
        }
        return result;
    }
    
    
    static String postfixToPrefix(String exp)
    {
        Stack<String> s = new Stack<String>();
        
        int length = exp.length();
        
        for (int i = 0; i < length; i++) {
            
            if (isOperator(exp.charAt(i))) {
                
                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();
                
                String temp
                = exp.charAt(i) + op2 + op1;
                
                s.push(temp);
            }
            
            else {
                s.push(exp.charAt(i) + "");
            }
        }
        
        String ans = "";
        for (String i : s)
        ans += i;
        return ans;
    }
    
    
    static String prefixToPostfix(String exp)
    {
        
        Stack<String> s = new Stack<String>();
        
        int length = exp.length();
        
        
        for (int i = length - 1; i >= 0; i--)
        {
            
            if (isOperator(exp.charAt(i)))
            {
                
                String op1 = s.peek();
                s.pop();
                String op2 = s.peek();
                s.pop();
                
                String temp = op1 + op2 + exp.charAt(i);
                
                s.push(temp);
            }
            
            else {
                
                s.push(exp.charAt(i) + "");
            }
        }
        return s.peek();
    }
    
    
    public static void main(String[] args)
    {
        
        System.out.println("Enter the expression: ");
        Scanner sc = new Scanner(System.in);
        String exp = sc.next();
        
        
        if(exp.charAt(0) == '+' || exp.charAt(0) == '-'|| exp.charAt(0) == '/' || exp.charAt(0) == '*' || exp.charAt(0) == '^'){
            System.out.println("\nThe expression is prefix");
            
            System.out.println("\nEnter your choice: \n1. Convert to infix \n2.Convert to postfix");
            
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                System.out.println("\nThe resultant Prefix to Infix Expression: \n" + prefixToInfix(exp));
                break;
                
                case 2:
                System.out.println("\nThe resultant Prefix to Postfix Expression: \n" + prefixToPostfix(exp));
                break;
                
                default:
                break;
                
            }
        }
        
        else if(exp.charAt(exp.length()-1) == '+' || exp.charAt(exp.length()-1) == '-' || exp.charAt(exp.length()-1) == '*' || exp.charAt(exp.length()-1) == '*' || exp.charAt(exp.length()-1) == '^'){
            System.out.println("\nThe expression is postfix");
            
            System.out.println("\nEnter your choice: \n1. Convert to infix \n2.Convert to prefix");
            
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                System.out.println("\nThe resultant Postfix to Infix Expression: \n" + postfixToInfix(exp));
                break;
                
                case 2:
                System.out.println("\nThe resultant Postfix to Prefix Expression: \n" + postfixToPrefix(exp));
                break;
                
                default:
                break;
            }
        }
        
        else{
            System.out.println("\nThe expression is infix");
            
            System.out.println("\nEnter your choice: \n1. Convert to prefix \n2. Convert to postfix");
            
            int choice = sc.nextInt();
            switch(choice){
                case 1:
                System.out.println("\nThe resultant Prefix Expression is: \n" + infixToPrefix(exp));
                break;
                
                case 2:
                System.out.println("\nThe resultant postfix expression is: \n" + infixToPostfix(exp));
                break;
                
                default:
                break;
            }
        }
    }
}






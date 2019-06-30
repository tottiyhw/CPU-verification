module Initial_model(clr,clk,P0,P1,P2,P3,P4,P,Op,IRFunc,Func,IRFunc20,OP00);
input clr,clk;
output P0;
output P1;
output P2;
output P3;
output P4;
output P;
input [5:0]Op;
input [5:0]IRFunc;
output [5:0]Func;
output IRFunc20;
output OP00;

assign IRFunc20=IRFunc20[5]&~IRFunc20[4]&~IRFunc20[3]&~IRFunc20[2]&~IRFunc20[1]&~IRFunc20[0];
assign OP00=~OP00[5]&~OP00[4]&~OP00[3]&~OP00[2]&~OP00[1]&~OP00[0];
endmodule

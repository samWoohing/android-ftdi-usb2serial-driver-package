%this function calculates the state distance of different nR input
%This calculation can be verified with CrackTest result.
%the newstate3 array indicates the distance of LFSR state, at the time when
%4bit ks3 is generated. index 1~8 corrisponds to bitwise 
%difference [0 1 2 3 4 5 6 7]*32 on nR^ks1
%
N=8;
fdbk=uint32(3);
InitState = uint64(round(rand(1,1)*2^48)*ones(1,N));

Input1 = uint32(round(rand(1,N)*2^32));
%Here, remember who is the last 3 bits of Input1, and should be xor-ed
Input2 = bitxor(Input1,uint32([0 1 2 3 4 5 6 7]*32));

[ks1, newstate1] = CRYPTO1_32(InitState, Input1, fdbk);
[ks1, newstate1] = CRYPTO1_32(newstate1, uint32(zeros(1,N)), uint32(1));
[ks1, newstate1] = CRYPTO1_4(newstate1, uint32(zeros(1,N)), uint32(1));

[ks2, newstate2] = CRYPTO1_32(InitState, Input2, fdbk);
[ks2, newstate2] = CRYPTO1_32(newstate2, uint32(zeros(1,N)), uint32(1));
[ks2, newstate2] = CRYPTO1_4(newstate2, uint32(zeros(1,N)), uint32(1));
%this linear test shows that the zero states and bitxor of inputs can give
%the correct distance of newstate1 and newstate2
%Also, linear feedback should be used here.
[ks3, newstate3] = CRYPTO1_32(uint64(zeros(1,N)), bitxor(Input1,Input2), fdbk);
[ks3, newstate3] = CRYPTO1_32(newstate3, uint32(zeros(1,N)), uint32(1));
[ks3, newstate3] = CRYPTO1_4(newstate3, uint32(zeros(1,N)), uint32(1));

disp('Linear check')
if sum(bitxor(newstate1,newstate2) == newstate3)/N == 1
    newstate3
end
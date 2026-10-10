# Jumbl
Custom coding language interpreter written in Java

## What is Jumbl?

Jumbl is just a fun custom coding language project I'm working on to learn how to design a language and test some interesting ideas.

Right now it doesn't do much, however, my goal is to get it to a state where it can technically be used just like any other standard language, like Java or Python.

Due to being run by an interpreter being written in Java (and very poorly coded), Jumbl is also quite slow and will not be remotely viable for use anytime soon. That being said I am interested in eventually trying to rewrite this in C as a fully-fledged compiler, so can actually be useful in some sense, maybe.

## Progress

As I said, Jumbl can't do much right now, it is turing complete with if statements and while loops. It can also store certain variables and perform basic logic and math operations. It can also print things to the console (python style).

The repo contains a small sample program in the form of main.jumbl. You can of course edit the code in there to test things for yourself. A little word of warning though, the code is pretty finnicky at the moment and certain edge-cases or things it hasn't been explicitly been designed to do (like concatenation) will most likely crash it. I also haven't added errors yet due to laziness. I am also going through the code a bit to fix some inefficiencies and other weird choices I made when I started this two years ago, mainly to make adding new features later a bit easier.

Here is a short example of the syntax so far

**Variables**
```
  int i = 20
  doub d = 13.6
  bool b = true
  string s = "Hello World"
```
So yeah, there's already some interesting design choices here. First off, no, there aren't any delimiters. As I was coding this I noticed I didn't have a need for a delimiter, like a ';' in C family languages. If you look at the parser's code, it just finishes the statement at the value and move on essentially. Hopefully this doesn't cause any issues later. I also called doubles "doub" and booleans "bool" because I think I'm special or something.

**If statements**
```
  if 12 > 15 {
    write "This will never get run"
  }
  elif 19 <= 39 {write "this will get run"}
  else {
    write "No, I haven't added comments yet, coding is hard"
  }
```
I pulled a python here and shortened else if, noticing a pattern yet? yes I also have write (and writeln!) statements because Jumbl wants to be python. No if conditions don't need parentheses, again I just didn't need to add it and it would have taken more effort to do so. 
**While loops**
```
  int i = 15
  loop {
    i -= 1
    write i
  } while i > 6
```
Ok, so my idea here is that you can have loops, loop whiles, and loop fors with loop statements. No, they to not work like do-while loops in java, those are useless. What Jumbl is saying here is, do this repeatedly while this statement is true.

## Future of Jumbl

So as stated earlier, Jumbl's code needs some serious TLC so it isn't a whole ordeal to add new features. 

That being said, I do have plans for new features, hopefully in the near future. At the moment, my main plans are to get some basic systems like concatenation, errors, and comments, that aren't super fun to add, but are very necessary for a functioning coding language. I also want to add for-loops and make loops work properly, I have a really fun idea for for-loop syntax that I'm excited to implement. After that I'll probably get started on functions. Those are gonna be a huge task and I haven't figured out the syntax quite yet. I know I don't want to copy java or C#, but there's a lot of things I still need to replicate their functionality (with some fun new gimmicks of course).

For the moment, here's my planned for-loop syntax, see if you can figure out how it works

```
loop {
  writeln "Foreach loops are take a while"
} for int i = 12 to 0 as i /= 3
```

## Why Jumbl?

My high school Comp Sci teacher one day walked up to me and said "You know, whenever I think of your name, I always think 'Jambalaya', I don't know why. Isn't that what Bart Simpson said or something?" and forevermore called my Jambalaya. My illiterate self, however, thought that Jambalaya was spelled Jumbalaya and therefore named the original iteration of this such (actually the original was SalsaScript, because my Comp Sci teacher had a weird obsession with salsa). Jumbalaya, however, is far too many syllables for the average sleep deprived programmer to say, so I decided to make it more hip and cool by shortening the name to Jumbl, because my language is a cool language, like Java, or Rust. Syllable minimization is the key to coolness friends, remember that.
